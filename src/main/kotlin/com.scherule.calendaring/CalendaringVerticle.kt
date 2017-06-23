package com.scherule.calendaring;

import io.vertx.core.AbstractVerticle;

class CalendaringVerticle : AbstractVerticle() {

    override fun start() {
        vertx.createHttpServer()
                .requestHandler({
                    it.response().end("Hello Vert.x!")
                })
        .listen(8080);
    }

}