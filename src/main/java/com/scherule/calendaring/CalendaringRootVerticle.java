package com.scherule.calendaring;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.rabbitmq.client.Channel;
import com.scherule.commons.MicroServiceVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.google.inject.name.Names.named;

@SuppressWarnings("unused")
public class CalendaringRootVerticle extends MicroServiceVerticle {

    private static final Logger log = LoggerFactory.getLogger(CalendaringRootVerticle.class);

    private final Channel schedulingChannel;
    private final SchedulingJobDispatcher schedulingJobDispatcher;
    private HttpServer httpRequestServer;

    @SuppressWarnings("unused")
    public CalendaringRootVerticle() {
        Injector injector = CalendaringApplication.context.getInjector();
        schedulingChannel = injector.getInstance(Key.get(Channel.class, named("scheduling.channel")));
        schedulingJobDispatcher = injector.getInstance(SchedulingJobDispatcher.class);
    }

    @Override
    public void start() {
        try {
            schedulingChannel.queueDeclare("scheduling-queue", true, false, false, null);
            schedulingJobDispatcher.dispatchJob();
        } catch (IOException e) {
            throw new IllegalStateException("Could not bind scheduling job consumer to scheduling channel", e);
        }
    }

    private void defineHttpServer() {
        String host = config().getString("http.host");
        int port = config().getInteger("http.port");

        Router router = Router.router(rxVertx);

        router.get("/hello").handler((request) -> {
            request.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(""));
        });

        httpRequestServer = rxVertx.createHttpServer(
                new HttpServerOptions().setPort(port).setHost(host)
        ).requestHandler(router::accept).listen();

        rxPublishHttpEndpoint(
                config().getString("http.name"),
                host,
                port
        ).doOnSuccess((t) -> {
            log.info("[Scheduling] http endpoint successfully published");
        }).doOnError((t) -> {
            log.error("[Scheduling] http endpoint could not be published");
        });

    }

}