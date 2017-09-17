package com.scherule.calendaring.domain

import org.joda.time.Duration
import org.joda.time.Interval
import javax.persistence.Embeddable

@Embeddable
class MeetingParameters(
        val between: Interval,
        val minDuration: Duration,
        val minParticipants: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as MeetingParameters

        if (between != other.between) return false
        if (minDuration != other.minDuration) return false
        if (minParticipants != other.minParticipants) return false

        return true
    }

    override fun hashCode(): Int {
        var result = between.hashCode()
        result = 31 * result + minDuration.hashCode()
        result = 31 * result + minParticipants
        return result
    }

    override fun toString(): String {
        return "MeetingParameters(between=$between, minDuration=$minDuration, minParticipants=$minParticipants)"
    }


}