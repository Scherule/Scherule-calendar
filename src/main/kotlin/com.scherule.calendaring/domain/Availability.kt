package com.scherule.calendaring.domain

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

}