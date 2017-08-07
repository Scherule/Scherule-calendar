package com.scherule.calendaring.domain.services

import com.google.inject.Inject
import com.scherule.calendaring.api.verticles.MeetingApi
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.repositories.MeetingRepository
import rx.Completable
import rx.Single

class MeetingService
@Inject constructor(
        private val meetingRepository: MeetingRepository
): MeetingApi {

    override fun getMeeting(meetingId: String): Single<Meeting> {
        return Single.just(meetingRepository.get(meetingId))
    }

    override fun removeMeeting(meetingId: Long): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateMeeting(meeting: Meeting): Single<Meeting> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createMeeting(meeting: Meeting): Single<Meeting> {
        meetingRepository.add(meeting)
        return Single.just(meeting)
    }

}