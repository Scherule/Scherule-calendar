package com.scherule.calendaring.domain

import com.scherule.calendaring.domain.MeetingId.Companion.newMeetingId

class Meeting(
        val meetingId: MeetingId = newMeetingId(),
        val parameters: MeetingParameters,
        val participants: Set<Participant>
) : AbstractEntity() {

    override fun equals(other: Any?): Boolean {
        if(!super.equals(other)) return false
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


}