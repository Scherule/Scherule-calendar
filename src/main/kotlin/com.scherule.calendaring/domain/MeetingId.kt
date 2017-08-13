package com.scherule.calendaring.domain

class MeetingId(
        val id: String
) {

    companion object {

        fun meetingId(id: String) : MeetingId {
            return MeetingId(id)
        }

        fun newMeetingId() : MeetingId {
            return meetingId(generateRandomHex(32))
        }

        fun noMeetingId(): MeetingId {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as MeetingId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "MeetingId(id='$id')"
    }

}