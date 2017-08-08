package com.scherule.calendaring.verticles;

import com.scherule.commons.MicroServiceVerticle;
import io.vertx.rxjava.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.RxHelper;
import io.vertx.rxjava.config.ConfigRetriever;
import io.vertx.rxjava.core.CompositeFuture;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.file.FileSystem;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.nio.charset.Charset;

import static io.vertx.rxjava.core.Future.succeededFuture;

public class WebControllerVerticle extends MicroServiceVerticle {

    private static final Logger log = LoggerFactory.getLogger(WebControllerVerticle.class);

    private ConfigRetriever configReader;

    @Override
    public void start(io.vertx.core.Future<Void> startFuture) {
        super.start(startFuture);
        configReader = ConfigRetriever.create(rxVertx);
        defineHttpServer(Future.<Void>future().setHandler(handler -> {
            if(handler.succeeded()) {
                startFuture.tryComplete();
            } else {
                startFuture.tryFail(handler.cause());
            }
        }));
    }

    private void defineHttpServer(Future<Void> startFuture) {
        final Router router = Router.router(rxVertx);

        CompositeFuture.all(
                configureCORS(router),
                configureSwaggerJSON(router),
                configureSwaggerUI(router),
                configureServer(router)
        ).setHandler(ar -> {
            if (ar.succeeded()) {
                startFuture.succeeded();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }

    private Future<Void> configureServer(Router router) {
        final Future<Void> outcome = Future.future();
        ObservableFuture<JsonObject> observable = RxHelper.observableFuture();
        configReader.getConfig(observable.toHandler());
        observable.subscribe(config -> {
            String host = config.getString("http.host", "localhost");
            int port = config.getInteger("http.port", 8080);

            rxVertx.createHttpServer(
                    new HttpServerOptions().setPort(port).setHost(host)
            ).requestHandler(router::accept).listen();

            rxPublishHttpEndpoint(
                    config.getString("http.name", "calendaring"),
                    host,
                    port
            )
                    .doOnCompleted(() -> {
                        log.info("[Scheduling] http endpoint successfully published");
                        outcome.succeeded();
                    })
                    .doOnError((t) -> {
                        log.error("[Scheduling] http endpoint could not be published");
                        outcome.fail(t);
                    });
        }, outcome::fail);
        return outcome;
    }

    private Future<Void> configureSwaggerJSON(Router router) {
        final Future<Void> outcome = Future.future();
        FileSystem fileSystem = rxVertx.fileSystem();
        ObservableFuture<Buffer> observable = RxHelper.observableFuture();
        observable.subscribe(
                swaggerFile -> {
                    router.get("/api/swagger.json").handler(
                            routingContext -> routingContext.response().end(
                                    swaggerFile.toString(String.valueOf(Charset.forName("utf-8")))
                            )
                    );
                    outcome.succeeded();
                },
                outcome::fail
        );
        fileSystem.readFile("swagger.json", observable.toHandler());
        return outcome;
    }

    private Future<Void> configureSwaggerUI(Router router) {
        router.route("/docs").handler(
                StaticHandler.create("src/main/resources/webroot/swagger-ui/index.html")
        );

        router.route("/docs/*").handler(
                StaticHandler.create("src/main/resources/webroot/swagger-ui")
        );
        return succeededFuture();
    }

    private Future<Void> configureCORS(Router router) {
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));
        return succeededFuture();
    }

}
