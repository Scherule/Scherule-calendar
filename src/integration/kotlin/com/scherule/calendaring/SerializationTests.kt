package com.scherule.calendaring

import com.fasterxml.jackson.databind.ObjectMapper
import com.scherule.calendaring.domain.*
import com.scherule.calendaring.endpoints.messaging.SchedulingJob
import org.joda.time.DateTimeZone
import org.joda.time.Duration
import org.joda.time.Interval
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.inject.Inject

@Nested
@DisplayName("Serialization tests")
@ExtendWith(InjectorExtension::class)
@InjectorContext(CalendaringDomainModule::class)
internal class SerializationTests {

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            DateTimeZone.setDefault(DateTimeZone.UTC)
        }

    }

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
    fun canSerializeMeetingId() {
        assertEquals(
                "{\"id\":\"123\"}",
                objectMapper.writeValueAsString(MeetingId.meetingId("123"))
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
    fun canSerializeSchedulingAlgorithm() {
        assertEquals(
                "{\"type\":\"intervalProjection\"}",
                objectMapper.writeValueAsString(SchedulingAlgorithm(
                        type = "intervalProjection"
                ))
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
    fun canSerializeMeeting() {
        assertEquals(
                "{\"id\":{\"id\":\"123\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"manager\":{\"id\":\"master\"},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}],\"keychain\":{\"managementKey\":{\"owner\":{\"id\":\"abc\"},\"hash\":\"\"},\"participationKeys\":{}},\"meetingState\":\"CREATED\"}",
                objectMapper.writeValueAsString(Meeting(
                        meetingId = MeetingId.meetingId("123"),
                        parameters = MeetingParameters(
                                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                minDuration = Duration.standardHours(5),
                                minParticipants = 3
                        ),
                        manager = ParticipantId.participantId("master"),
                        participants = setOf(
                                Participant(
                                        id = ParticipantId.participantId("321"),
                                        name = "Greg",
                                        importance = 100,
                                        availability = setOf(
                                                Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                                                Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                                        )
                                )
                        ),
                        meetingState = MeetingState.CREATED
                ))
        )
    }

    @Test
    fun canDeserializeMeeting() {
        assertEquals(
                Meeting(
                        meetingId = MeetingId.meetingId("123"),
                        parameters = MeetingParameters(
                                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                minDuration = Duration.standardHours(5),
                                minParticipants = 3
                        ),
                        manager = ParticipantId.participantId("master"),
                        participants = setOf(
                                Participant(
                                        id = ParticipantId.participantId("321"),
                                        name = "Greg",
                                        importance = 100,
                                        availability = setOf(
                                                Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                                                Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                                        )
                                )
                        ),
                        meetingState = MeetingState.CREATED
                ),
                objectMapper.readValue("{\"id\":{\"id\":\"123\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"manager\":{\"id\":\"master\"},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}],\"keychain\":{\"managementKey\":{\"owner\":{\"id\":\"abc\"},\"hash\":\"\"},\"participationKeys\":{}},\"meetingState\":\"CREATED\"}", Meeting::class.java)
        )
    }

    @Test
    fun canSerializeSchedulingJob() {
        assertEquals(
                "{\"id\":{\"id\":\"933\"},\"algorithm\":{\"type\":\"intervalProjection\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":1,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1}]}]}",
                objectMapper.writeValueAsString(SchedulingJob(
                        id = SchedulingJobId.schedulingJobId("933"),
                        algorithm = SchedulingAlgorithm(
                                type = "intervalProjection"
                        ),
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