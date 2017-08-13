package com.scherule.calendaring.domain.services

import com.google.inject.Inject
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.MeetingId
import com.scherule.calendaring.domain.repositories.MeetingRepository
import rx.Completable
import rx.Single

class MeetingService
@Inject constructor(
        private val meetingRepository: MeetingRepository
){

    fun getMeeting(meetingId: MeetingId): Single<Meeting> {
        return Single.just(meetingRepository.get(meetingId.id))
    }

    fun removeMeeting(meetingId: Long): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateMeeting(meeting: Meeting): Single<Meeting> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createMeeting(meeting: Meeting): Single<Meeting> {
        val boundMeeting = meeting.copy(MeetingId.meetingId("123"))
        meetingRepository.add(boundMeeting)
        return Single.just(boundMeeting)
    }

}