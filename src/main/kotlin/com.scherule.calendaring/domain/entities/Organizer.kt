package com.scherule.calendaring.domain.entities

import java.io.Serializable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ORGANIZER")
class Organizer(

        @EmbeddedId
        val participantId: ParticipantId

): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organizer

        if (participantId != other.participantId) return false

        return true
    }

    override fun hashCode(): Int {
        return participantId.hashCode()
    }

}