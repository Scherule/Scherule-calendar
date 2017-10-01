package com.scherule.calendaring.domain.entities

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "MEETING_KEY")
data class MeetingKey(

        @EmbeddedId
        val owner: ParticipantId,

        val hash: String

)