package com.scherule.calendaring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.scherule.calendaring.SchedulingJob
import org.joda.time.Duration
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
    fun canSerializeAvailability() {
        assertEquals(
                "{\"interval\":\"1507040100000-1507046400000\",\"preference\":90}",
                objectMapper.writeValueAsString(Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"), 90))
        )
    }

    @Test
    fun canSerializeParticipant() {
        assertEquals(
                "{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}",
                objectMapper.writeValueAsString(Participant(
                        id = ParticipantId.participantId("321"),
                        name = "Greg",
                        importance = 100,
                        availability = setOf(
                                Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                                Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                        )
                ))
        )
    }

    @Test
    fun canSerializeMeetingParameters() {
        assertEquals(
                "{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3}",
                objectMapper.writeValueAsString(MeetingParameters(
                        between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                        minDuration = Duration.standardHours(5),
                        minParticipants = 3
                ))
        )
    }

    @Test
    fun canSerializeSchedulingJob() {
        assertEquals(
                "{\"id\":{\"id\":\"933\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":1,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1}]}]}",
                objectMapper.writeValueAsString(SchedulingJob(
                        id = SchedulingJobId.schedulingJobId("933"),
                        parameters = MeetingParameters(
                                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                minDuration = Duration.standardHours(5),
                                minParticipants = 3
                        ),
                        participants = setOf(
                                Participant(
                                        id = ParticipantId.participantId("321"),
                                        name = "Greg",
                                        importance = 1,
                                        availability = setOf(
                                                Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"))
                                        )
                                )
                        )
                ))
        )
    }

}