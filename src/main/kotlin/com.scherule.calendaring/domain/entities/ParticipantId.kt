package com.scherule.calendaring.domain.entities

class ParticipantId(val id: String) {

    companion object {
        fun participantId(id: String) : ParticipantId {
            return ParticipantId(id)
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