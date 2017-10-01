package com.scherule.calendaring.domain.entities

import java.io.Serializable
import java.util.*
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
@Access(AccessType.FIELD)
class ParticipantId(

        @Column(name = "participant_id", nullable = false, updatable = false)
        val id: UUID

) : Serializable {

    companion object {

        fun participantId(id: String): ParticipantId {
            return ParticipantId(UUID.fromString(id))
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ParticipantId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ParticipantId(id=$id)"
    }

}