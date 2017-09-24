package com.scherule.calendaring.domain.entities

data class MeetingKey(
        val owner: ParticipantId,
        val hash: String
)