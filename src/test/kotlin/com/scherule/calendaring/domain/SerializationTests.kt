package com.scherule.calendaring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.joda.time.Interval
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.inject.Inject

@ExtendWith(InjectorExtension::class)
internal class SerializationTests {

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Test
    fun canSerializeParticipantId() {
        assertEquals(
                "{\"id\":\"123\"}",
                objectMapper.writeValueAsString(ParticipantId.participantId("123"))
        )
    }

    @Test
    fun canSerializeSchedulingJobId() {
        assertEquals(
                "{\"id\":\"123\"}",
                objectMapper.writeValueAsString(SchedulingJobId.schedulingJobId("123"))
        )
    }

    @Test
    fun canSerializeParticipant() {
        assertEquals(
                "{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"availability\":[\"1507040100000-1507046400000\",\"1507108500000-1507158000000\"]}",
                objectMapper.writeValueAsString(Participant(
                        id = ParticipantId.participantId("321"),
                        name = "Greg",
                        availability = setOf(
                                Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z")
                        )
                ))
        )
    }

}