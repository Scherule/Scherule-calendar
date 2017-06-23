package io.vertx.starter;

import io.vertx.core.AbstractVerticle;

public class MainVerticle : AbstractVerticle {

    @Override
    public start() {
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Hello Vert.x!"))
        .listen(8080);
    }

}