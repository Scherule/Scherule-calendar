package com.scherule.calendaring.builders

import com.scherule.calendaring.domain.entities.Meeting
import com.scherule.calendaring.domain.entities.MeetingId.Companion.noMeetingId
import com.scherule.calendaring.domain.entities.MeetingParameters
import com.scherule.calendaring.domain.entities.Participant
import com.scherule.calendaring.domain.entities.ParticipantId
import com.scherule.calendaring.domain.entities.ParticipantId.Companion.participantId
import org.joda.time.Duration
import org.joda.time.Interval

class MeetingBuilder {

    private var meetingId = noMeetingId
    private var between = Interval.parse("2017-10-01T00:00Z/2017-10-30T16:00Z")
    private var minParticipants = 1
    private var minDuration = Duration.standardHours(5)
    private var participants: MutableSet<Participant> = mutableSetOf()
    private var manager = participantId("manager")

    companion object {
        fun aMeeting() = MeetingBuilder()
    }

    fun inBetween(between: Interval) = apply {
        this.between = between
    }

    fun withMinParticipants(minParticipants: Int) = apply {
        this.minParticipants = minParticipants
    }

    fun withParticipants(vararg participants: Participant) = apply {
        this.participants.addAll(participants)
    }

    fun withManager(participantId: ParticipantId) = apply {
        this.manager = participantId;
    }

    fun build(): Meeting = Meeting(
            meetingId = meetingId,
            parameters = MeetingParameters(
                    between,
                    minDuration,
                    minParticipants
            ),
            manager = manager,
            participants = participants
    )

}