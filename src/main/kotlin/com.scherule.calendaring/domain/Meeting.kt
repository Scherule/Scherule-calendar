package com.scherule.calendaring.domain

import com.scherule.calendaring.domain.MeetingId.Companion.newMeetingId

class Meeting(
        val id: MeetingId = newMeetingId(),
        val parameters: MeetingParameters,
        val participants: Set<Participant>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Meeting

        if (id != other.id) return false
        if (parameters != other.parameters) return false
        if (participants != other.participants) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + participants.hashCode()
        return result
    }

    override fun toString(): String {
        return "Meeting(id=$id, parameters=$parameters, participants=$participants)"
    }


}