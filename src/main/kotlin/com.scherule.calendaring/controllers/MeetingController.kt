package com.scherule.calendaring.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.google.inject.Singleton
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.MeetingId
import com.scherule.calendaring.domain.services.MeetingService
import io.vertx.rxjava.ext.web.RoutingContext

@Singleton
class MeetingController
@Inject
constructor(
        val meetingService: MeetingService,
        val objectMapper: ObjectMapper
){

    fun getMeeting(routingContext: RoutingContext) {
        val meetingId = routingContext.request().getParam("meetingId")
        meetingService.getMeeting(MeetingId.meetingId(meetingId)).subscribe {
            meeting -> routingContext.response().setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(objectMapper.writeValueAsString(meeting))
        }
    }

    fun postMeeting(routingContext: RoutingContext) {
        val meetingDetails = objectMapper.readValue<Meeting>(routingContext.bodyAsString, Meeting::class.java)
        meetingService.createMeeting(meetingDetails).subscribe {
            meeting -> routingContext.response().setStatusCode(201)
                .putHeader("content-type", "application/json")
                .end(objectMapper.writeValueAsString(meeting))
        }
    }

    fun putMeeting(routingContext: RoutingContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteMeeting(routingContext: RoutingContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}