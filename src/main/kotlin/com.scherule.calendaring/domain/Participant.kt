package com.scherule.calendaring.domain

import org.joda.time.Interval

class Participant(
        val id: ParticipantId,
        val name: String,
        val importance: Int,
        val availability: Set<Interval>
)