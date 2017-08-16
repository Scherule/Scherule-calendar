package com.scherule.calendaring.domain

class MeetingId(
        val id: String? = null
) : Identifiable {

    override fun getIdentifier(): String? = id

    companion object {

        val noMeetingId = MeetingId()

        fun meetingId(id: String?): MeetingId {
            return MeetingId(id)
        }

        fun newMeetingId(): MeetingId {
            return meetingId(generateRandomHex(32))
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
        return if (id === null) 1 else id.hashCode()
    }

    override fun toString(): String {
        return "MeetingId(id='$id')"
    }

}