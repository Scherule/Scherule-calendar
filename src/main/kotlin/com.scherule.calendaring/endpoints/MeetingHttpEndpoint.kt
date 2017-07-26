package com.scherule.calendaring.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.repositories.MeetingRepository
import io.vertx.core.json.Json
import io.vertx.rxjava.ext.web.Router
import io.vertx.rxjava.ext.web.RoutingContext
import io.vertx.rxjava.ext.web.handler.BodyHandler

class MeetingHttpEndpoint
@Inject constructor(
        private val objectMapper: ObjectMapper,
        private val meetingRepository: MeetingRepository
) : Endpoint {

    companion object {
        val BASE_URL = "/meeting"
    }

    override fun mount(router: Router) {
        router.route(BASE_URL).handler(BodyHandler.create())

        router.post(BASE_URL).handler(this::createMeeting)


        router.get(BASE_URL).handler { request ->
            request.response()
                    .putHeader("content-type", "application/json")
                    .end(Json.encode("{ \"abc\": 1 }"))
        }
    }

    fun createMeeting(routingContext: RoutingContext) {
        val meeting = objectMapper.readValue(routingContext.bodyAsString, Meeting::class.java)
        meetingRepository.add(meeting)
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(objectMapper.writeValueAsString(meeting));
    }

}