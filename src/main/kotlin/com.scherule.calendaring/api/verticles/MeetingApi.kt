package com.scherule.calendaring.api.verticles

import com.scherule.calendaring.domain.Meeting
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

interface MeetingApi {

    fun createMeeting(meeting: Meeting, handler: Handler<AsyncResult<Meeting>>)

    fun getMeeting(meetingId: String, handler: Handler<AsyncResult<Meeting>>)

    fun removeMeeting(meetingId: Long, handler: Handler<AsyncResult<Void>>)

    fun updateMeeting(meeting: Meeting, handler: Handler<AsyncResult<Meeting>>)

}
