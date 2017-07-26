package com.scherule.calendaring.endpoints

import io.vertx.core.json.Json
import io.vertx.rxjava.ext.web.Router


class MeetingHttpEndpoint : Endpoint {

    override fun mount(router: Router) {
        router.get("/hello").handler { request ->
            request.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode("{ \"abc\": 1 }"))
        }
    }
}