package com.scherule.calendaring.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.google.inject.Singleton
import com.scherule.calendaring.domain.Meeting
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
        val meeting = meetingService.getMeeting(meetingId)
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(objectMapper.writeValueAsString(meeting))
    }

    fun postMeeting(routingContext: RoutingContext) {
        val meetingToCreate = objectMapper.readValue<Meeting>(routingContext.bodyAsString, Meeting::class.java)
        meetingService.createMeeting(meetingToCreate)
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(objectMapper.writeValueAsString(meetingToCreate))
    }

    fun putMeeting(routingContext: RoutingContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteMeeting(routingContext: RoutingContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}