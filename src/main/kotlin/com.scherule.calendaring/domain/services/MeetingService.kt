package com.scherule.calendaring.domain.services

import com.scherule.calendaring.domain.entities.Meeting
import com.scherule.calendaring.domain.entities.MeetingId
import com.scherule.calendaring.domain.repositories.MeetingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MeetingService
@Autowired constructor(
        private val meetingRepository: MeetingRepository,
        private val keychainGenerator: KeychainGenerator
) {

    fun getMeeting(meetingId: MeetingId): Meeting {
        return meetingRepository.findOne(meetingId)
    }

    fun createMeeting(meeting: Meeting): Meeting {
        val createdMeeting = meeting.generateKeys(keychainGenerator)
        meetingRepository.save(createdMeeting)
        return createdMeeting
    }

}