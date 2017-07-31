package com.scherule.calendaring.domain.services

import com.google.inject.Inject
import com.scherule.calendaring.api.verticles.MeetingApi
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.repositories.MeetingRepository
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler

class MeetingService
@Inject constructor(
        private val meetingRepository: MeetingRepository
): MeetingApi {

    override fun createMeeting(meeting: Meeting, handler: Handler<AsyncResult<Meeting>>) {
        meetingRepository.add(meeting)
        handler.handle(Future.succeededFuture(meeting))
    }

    override fun getMeeting(meetingId: String, handler: Handler<AsyncResult<Meeting>>) {
        handler.handle(Future.succeededFuture());
    }

    override fun removeMeeting(meetingId: Long, handler: Handler<AsyncResult<Void>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateMeeting(meeting: Meeting, handler: Handler<AsyncResult<Meeting>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}