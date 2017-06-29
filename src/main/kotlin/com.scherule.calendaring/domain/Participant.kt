package com.scherule.calendaring.domain

class Participant(
        val id: ParticipantId,
        val name: String,
        val importance: Int,
        val availability: Set<Availability>
)