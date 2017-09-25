package com.scherule.calendaring.builders

import com.scherule.calendaring.domain.entities.Availability
import com.scherule.calendaring.domain.entities.Participant
import com.scherule.calendaring.domain.entities.ParticipantId
import org.joda.time.Interval

class ParticipantBuilder(
        private val name: String
) {

    private var importance: Int = 1
    private var availabilitySet: MutableSet<Availability> = mutableSetOf()

    companion object {
        fun aParticipant(name: String): ParticipantBuilder = ParticipantBuilder(name)
    }

    fun withImportance(importance: Int) = apply {
        this.importance = importance;
    }

    fun withAvailability(interval: Interval) = apply {
        availabilitySet.add(Availability(interval, 1))
    }

    fun build(): Participant {
        return Participant(
                ParticipantId.participantId(name),
                name,
                importance,
                availabilitySet
        )
    }


}