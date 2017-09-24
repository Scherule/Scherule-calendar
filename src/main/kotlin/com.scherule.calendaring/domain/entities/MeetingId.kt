package com.scherule.calendaring.domain.entities

import com.scherule.calendaring.domain.generateRandomHex
import java.io.Serializable

class MeetingId(
        val id: String
) : Serializable {

    companion object {

        val noMeetingId = MeetingId("")

        fun meetingId(id: String): MeetingId {
            return MeetingId(id)
        }

        fun newMeetingId(): MeetingId {
            return meetingId(generateRandomHex(32))
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