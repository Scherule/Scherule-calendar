package com.scherule.calendaring.domain.entities

import com.scherule.calendaring.domain.ZERO_UUID
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MeetingId(

        @Column(name = "meeting_id", nullable = false, updatable = false)
        val id: UUID

) : Serializable {

    companion object {

        val noMeetingId = MeetingId(ZERO_UUID)

        fun meetingId(id: String): MeetingId {
            return MeetingId(UUID.fromString(id))
        }

        fun newMeetingId(): MeetingId {
            return meetingId(UUID.randomUUID().toString())
        }

    }

    override fun toString(): String {
        return "MeetingId(id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeetingId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}