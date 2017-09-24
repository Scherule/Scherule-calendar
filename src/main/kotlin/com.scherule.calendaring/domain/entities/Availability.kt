package com.scherule.calendaring.domain.entities

import org.joda.time.Interval

class Availability(
        val interval: Interval,
        val preference: Int
) {

    companion object {

        fun availableIn(interval: Interval, preference: Int = 1): Availability {
            return Availability(interval, preference);
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Availability

        if (interval != other.interval) return false
        if (preference != other.preference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = interval.hashCode()
        result = 31 * result + preference
        return result
    }

    override fun toString(): String {
        return "Availability(interval=$interval, preference=$preference)"
    }


}