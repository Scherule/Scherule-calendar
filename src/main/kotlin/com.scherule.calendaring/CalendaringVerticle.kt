package com.scherule.calendaring;

import io.vertx.core.AbstractVerticle
import io.vertx.servicediscovery.ServiceDiscovery.create
import io.vertx.servicediscovery.ServiceDiscoveryOptions


class CalendaringVerticle : AbstractVerticle() {

    override fun start() {
        val discovery = create(vertx,
                ServiceDiscoveryOptions()
                        .setAnnounceAddress("scherule-announcing")
                        .setName("calendaring"))

        vertx.createHttpServer()
                .requestHandler({
                    it.response().end("Hello Vert.x bla bla!")
                })
                .listen(config().getInteger("http.port", 8083), {
                    if (it.succeeded()) {
                        System.out.println("Server started");
                    } else {
                        System.out.println("Cannot start the server: " + it.cause());
                    }
                })

        discovery.close()
    }

}