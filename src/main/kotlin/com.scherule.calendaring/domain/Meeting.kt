package com.scherule.calendaring.domain

class Meeting(
        val parameters: MeetingParameters,
        val participants: Set<Participant>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Meeting

        if (parameters != other.parameters) return false
        if (participants != other.participants) return false

        return true
    }

    override fun hashCode(): Int {
        var result = parameters.hashCode()
        result = 31 * result + participants.hashCode()
        return result
    }

    override fun toString(): String {
        return "Meeting(parameters=$parameters, participants=$participants)"
    }

}