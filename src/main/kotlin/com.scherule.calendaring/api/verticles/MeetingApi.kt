package com.scherule.calendaring.api.verticles

import com.scherule.calendaring.domain.Meeting
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import rx.Observable

interface MeetingApi {

    fun createMeeting(meeting: Meeting): Observable<Meeting>

    fun getMeeting(meetingId: String, handler: Handler<AsyncResult<Meeting>>)

    fun removeMeeting(meetingId: Long, handler: Handler<AsyncResult<Void>>)

    fun updateMeeting(meeting: Meeting, handler: Handler<AsyncResult<Meeting>>)

}
