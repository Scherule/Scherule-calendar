package com.scherule.calendaring.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.scherule.calendaring.domain.MeetingId.Companion.meetingId
import com.scherule.calendaring.domain.MeetingId.Companion.noMeetingId
import com.scherule.calendaring.domain.MeetingKeychain.Companion.EMPTY_KEYCHAIN

class Meeting(
        meetingId: MeetingId = noMeetingId,
        revision: String? = null,
        val parameters: MeetingParameters,
        val manager: ParticipantId,
        val participants: Set<Participant>,
        val keychain: MeetingKeychain = EMPTY_KEYCHAIN,
        val meetingState: MeetingState = MeetingState.INITIAL
) : AbstractEntity(meetingId.id, revision) {

    @JsonIgnore
    fun getMeetingId() = meetingId(id)

    fun generateKeys(
            keychainGenerator: KeychainGenerator
    ): Meeting {
        return Meeting(
                noMeetingId,
                revision,
                parameters,
                manager,
                participants,
                keychainGenerator.generateFor(this),
                MeetingState.CREATED
        )
    }

}