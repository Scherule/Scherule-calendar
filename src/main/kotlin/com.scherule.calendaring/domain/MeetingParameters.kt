package com.scherule.calendaring.domain

import org.joda.time.Duration
import org.joda.time.Interval

class MeetingParameters(
        val between: Interval,
        val minDuration: Duration,
        val minParticipants: Int
)