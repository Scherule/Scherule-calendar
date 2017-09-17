package com.scherule.calendaring.domain.services

import com.google.inject.Inject
import com.scherule.calendaring.domain.services.KeychainGenerator
import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.MeetingId
import com.scherule.calendaring.domain.repositories.MeetingRepository
import rx.Single

class MeetingService
@Inject constructor(
        private val meetingRepository: MeetingRepository,
        private val keychainGenerator: KeychainGenerator
) {

    fun getMeeting(meetingId: MeetingId): Single<Meeting> {
        return Single.just(meetingRepository.findOne(meetingId))
    }

    fun createMeeting(meeting: Meeting): Meeting {
        val createdMeeting = meeting.generateKeys(keychainGenerator)
        meetingRepository.save(createdMeeting)
        return createdMeeting
    }

}