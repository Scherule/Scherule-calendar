package com.scherule.calendaring.domain

class Participant(
        val id: ParticipantId,
        val name: String,
        val importance: Int,
        val availability: Set<Availability>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Participant

        if (id != other.id) return false
        if (name != other.name) return false
        if (importance != other.importance) return false
        if (availability != other.availability) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + importance
        result = 31 * result + availability.hashCode()
        return result
    }

    override fun toString(): String {
        return "Participant(id=$id, name='$name', importance=$importance, availability=$availability)"
    }


}