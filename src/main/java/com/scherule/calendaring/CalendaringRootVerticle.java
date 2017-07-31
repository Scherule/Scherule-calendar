package com.scherule.calendaring;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.phiz71.rxjava.vertx.swagger.router.SwaggerRouter;
import com.github.phiz71.vertx.swagger.router.OperationIdServiceIdResolver;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.rabbitmq.client.Channel;
import com.scherule.calendaring.api.verticles.MeetingApiVerticle;
import com.scherule.commons.MicroServiceVerticle;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.google.inject.name.Names.named;

@SuppressWarnings("unused")
public class CalendaringRootVerticle extends MicroServiceVerticle {

    private static final Logger log = LoggerFactory.getLogger(CalendaringRootVerticle.class);

    private final Channel schedulingChannel;
    private final SchedulingJobDispatcher schedulingJobDispatcher;
    private final SchedulingResultsConsumer schedulingResultsConsumer;
    private final MeetingApiVerticle meetingApiVerticle;

    private HttpServer httpRequestServer;

    @SuppressWarnings("unused")
    public CalendaringRootVerticle() {
        Injector injector = CalendaringApplication.context.getInjector();
        schedulingChannel = injector.getInstance(Key.get(Channel.class, named("scheduling.channel")));
        schedulingJobDispatcher = injector.getInstance(SchedulingJobDispatcher.class);
        schedulingResultsConsumer = injector.getInstance(SchedulingResultsConsumer.class);
        meetingApiVerticle = injector.getInstance(MeetingApiVerticle.class);
    }

    @Override
    public void start(Future<Void> startFuture) {
        super.start(startFuture);
        try {
            schedulingChannel.queueDeclare("scheduling-queue", true, false, false, null);
            schedulingChannel.queueDeclare("scheduling-queue-results", true, false, false, null);
            schedulingChannel.basicConsume("scheduling-queue-results", false, schedulingResultsConsumer);
            schedulingJobDispatcher.dispatchJob();
        } catch (IOException e) {
            throw new IllegalStateException("Could not bind scheduling job consumer to scheduling channel", e);
        }

        defineHttpServer(startFuture);
    }

    private void deployVerticles(Future<Void> startFuture) {
        vertx.deployVerticle(meetingApiVerticle, res -> {
            if (res.succeeded()) {
                log.info("MeetingApiVerticle : Deployed");
            } else {
                startFuture.fail(res.cause());
                log.error("MeetingApiVerticle : Deployement failed");
            }
        });

        vertx.deployVerticle("com.scherule.calendaring.api.verticles.SwaggerApiVerticle", res -> {
            if (res.succeeded()) {
                log.info("SwaggerApiVerticle : Deployed");
            } else {
                startFuture.fail(res.cause());
                log.error("SwaggerApiVerticle : Deployement failed");
            }
        });
    }

    private void defineHttpServer(Future<Void> startFuture) {
        ConfigRetriever retriever = ConfigRetriever.create(vertx);

        Json.mapper.registerModule(new JavaTimeModule());
        FileSystem vertxFileSystem = vertx.fileSystem();

        vertxFileSystem.readFile("swagger.json", swaggerFile -> {
            if (swaggerFile.succeeded()) {
                Swagger swagger = new SwaggerParser().parse(swaggerFile.result().toString(Charset.forName("utf-8")));
                Router swaggerRouter = SwaggerRouter.swaggerRouter(
                        Router.router(rxVertx), swagger,
                        rxVertx.eventBus(),
                        new OperationIdServiceIdResolver()
                );

                deployVerticles(startFuture);

                retriever.getConfig(ar -> {
                    if (ar.failed()) {
                        throw new IllegalStateException("Could not retrieve config configuration");
                    } else {
                        JsonObject config = ar.result();

                        String host = config.getString("http.host");
                        int port = config.getInteger("http.port");

                        Router router = Router.router(rxVertx);

                        httpRequestServer = rxVertx.createHttpServer(
                                new HttpServerOptions().setPort(port).setHost(host)
                        ).requestHandler(swaggerRouter::accept).listen();

                        rxPublishHttpEndpoint(
                                config.getString("http.name"),
                                host,
                                port
                        ).doOnSuccess((t) -> {
                            log.info("[Scheduling] http endpoint successfully published");
                            startFuture.complete();
                        }).doOnError((t) -> {
                            log.error("[Scheduling] http endpoint could not be published");
                        });

                    }
                });
            } else {
                startFuture.fail(swaggerFile.cause());
            }
        });

    }

}