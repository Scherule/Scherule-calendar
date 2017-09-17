package com.scherule.calendaring.domain.services

import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.MeetingKey
import com.scherule.calendaring.domain.MeetingKeychain
import com.scherule.calendaring.domain.generateRandomHex
import org.springframework.stereotype.Service

@Service
class KeychainGenerator {

    fun generateFor(meeting: Meeting): MeetingKeychain {
        return MeetingKeychain(
                managementKey = MeetingKey(meeting.manager, generateRandomHex(32)),
                participationKeys = meeting.participants.map {
                    it.participantId to MeetingKey(it.participantId, generateRandomHex(32))
                }.toMap()
        )
    }

}