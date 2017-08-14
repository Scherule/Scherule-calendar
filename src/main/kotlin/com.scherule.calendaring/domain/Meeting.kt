package com.scherule.calendaring.domain

import com.scherule.calendaring.domain.MeetingId.Companion.meetingId
import com.scherule.calendaring.domain.MeetingId.Companion.noMeetingId
import com.scherule.calendaring.domain.MeetingKeychain.Companion.EMPTY_KEYCHAIN

class Meeting(
        meetingId: MeetingId = noMeetingId,
        val parameters: MeetingParameters,
        val manager: ParticipantId,
        val participants: Set<Participant>,
        val keychain: MeetingKeychain = EMPTY_KEYCHAIN,
        val meetingState: MeetingState = MeetingState.INITIAL
) : AbstractEntity(meetingId) {

    fun getMeetingId() = meetingId(id!!)

    fun initiate(
            newMeetingId: MeetingId,
            keychainGenerator: KeychainGenerator
    ): Meeting {
        return Meeting(
                newMeetingId,
                parameters,
                manager,
                participants,
                keychainGenerator.generateFor(this),
                MeetingState.CREATED
        )
    }

}