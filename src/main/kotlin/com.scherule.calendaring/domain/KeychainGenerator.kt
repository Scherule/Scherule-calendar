package com.scherule.calendaring.domain

import com.google.inject.Singleton

@Singleton
class KeychainGenerator {

    fun generateFor(meeting: Meeting): MeetingKeychain {
        return MeetingKeychain(
                managementKey = MeetingKey(meeting.manager, generateRandomHex(32)),
                participationKeys = meeting.participants.map {
                    it.id to MeetingKey(it.id, generateRandomHex(32))
                }.toMap()
        )
    }

}