package com.scherule.calendaring.domain

import com.scherule.calendaring.domain.MeetingId.Companion.newMeetingId
import com.scherule.calendaring.domain.MeetingKeychain.Companion.EMPTY_KEYCHAIN

class Meeting(
        val meetingId: MeetingId = newMeetingId(),
        val parameters: MeetingParameters,
        val manager: ParticipantId,
        val participants: Set<Participant>,
        val keychain: MeetingKeychain = EMPTY_KEYCHAIN,
        val meetingState: MeetingState = MeetingState.INITIAL
) : AbstractEntity() {

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as Meeting

        if (meetingId != other.meetingId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + meetingId.hashCode()
        return result
    }

    override fun toString(): String {
        return "Meeting(id=$id, parameters=$parameters, participants=$participants)"
    }

    fun create(
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