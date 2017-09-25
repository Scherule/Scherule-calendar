package com.scherule.calendaring.domain.services

import com.scherule.calendaring.domain.entities.Meeting
import com.scherule.calendaring.domain.entities.MeetingKey
import com.scherule.calendaring.domain.entities.MeetingKeychain
import com.scherule.calendaring.domain.generateRandomHex
import org.springframework.stereotype.Service

@Service
class KeychainGenerator {

    fun generateFor(meeting: Meeting): MeetingKeychain {
        return MeetingKeychain(
                managementKey = generateRandomHex(32),
                participationKeys = meeting.participants.map {
                    it.participantId to MeetingKey(it.participantId, generateRandomHex(32))
                }.toMap()
        )
    }

}