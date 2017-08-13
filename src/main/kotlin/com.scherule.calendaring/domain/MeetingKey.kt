package com.scherule.calendaring.domain

data class MeetingKey(
        val owner: ParticipantId,
        val hash: String
)