package com.scherule.calendaring.regular

import com.fasterxml.jackson.databind.ObjectMapper
import com.scherule.calendaring.AbstractIntegrationTest
import com.scherule.calendaring.domain.*
import com.scherule.calendaring.endpoints.messaging.SchedulingJob
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTimeZone
import org.joda.time.Duration
import org.joda.time.Interval
import org.junit.BeforeClass
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

internal class SerializationTests : AbstractIntegrationTest() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            DateTimeZone.setDefault(DateTimeZone.UTC)
        }

    }

    @Test
    fun canSerializeParticipantId() {
        assertThat(objectMapper.writeValueAsString(ParticipantId.participantId("123")))
                .isEqualTo("{\"id\":\"123\"}")
    }

    @Test
    fun canSerializeMeetingId() {
        assertThat(objectMapper.writeValueAsString(MeetingId.meetingId("123")))
                .isEqualTo("{\"id\":\"123\"}")
    }

    @Test
    fun canSerializeSchedulingJobId() {
        assertThat(objectMapper.writeValueAsString(SchedulingJobId.schedulingJobId("123")))
                .isEqualTo("{\"id\":\"123\"}")
    }


    @Test
    fun canSerializeAvailability() {
        assertThat(objectMapper.writeValueAsString(Availability.availableIn(
                Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"), 90)
        )).isEqualTo("{\"interval\":\"1507040100000-1507046400000\",\"preference\":90}")
    }

    @Test
    fun canSerializeSchedulingAlgorithm() {
        assertThat(objectMapper.writeValueAsString(SchedulingAlgorithm(
                type = "intervalProjection"
        ))).isEqualTo("{\"type\":\"intervalProjection\"}")
    }

    @Test
    fun canSerializeParticipant() {
        assertThat(objectMapper.writeValueAsString(Participant(
                participantId = ParticipantId.participantId("321"),
                name = "Greg",
                importance = 100,
                availability = setOf(
                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                        Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                )
        ))).isEqualTo("{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}")
    }

    @Test
    fun canSerializeMeetingParameters() {
        assertThat(objectMapper.writeValueAsString(MeetingParameters(
                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                minDuration = Duration.standardHours(5),
                minParticipants = 3
        ))).isEqualTo("{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3}")
    }

    @Test
    fun canSerializeMeeting() {
        assertThat(objectMapper.writeValueAsString(Meeting(
                meetingId = MeetingId.meetingId("123"),
                parameters = MeetingParameters(
                        between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                        minDuration = Duration.standardHours(5),
                        minParticipants = 3
                ),
                manager = ParticipantId.participantId("master"),
                participants = setOf(
                        Participant(
                                participantId = ParticipantId.participantId("321"),
                                name = "Greg",
                                importance = 100,
                                availability = setOf(
                                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                                        Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                                )
                        )
                ),
                meetingState = MeetingState.CREATED
        ))).isEqualTo("{\"id\":{\"id\":\"123\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"manager\":{\"id\":\"master\"},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}],\"keychain\":{\"managementKey\":{\"owner\":{\"id\":\"abc\"},\"hash\":\"\"},\"participationKeys\":{}},\"meetingState\":\"CREATED\"}")
    }

    @Test
    fun canDeserializeMeeting() {
        assertThat(objectMapper.readValue("{\"id\":{\"id\":\"123\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"manager\":{\"id\":\"master\"},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}],\"keychain\":{\"managementKey\":{\"owner\":{\"id\":\"abc\"},\"hash\":\"\"},\"participationKeys\":{}},\"meetingState\":\"CREATED\"}", Meeting::class.java))
                .isEqualTo(Meeting(
                        meetingId = MeetingId.meetingId("123"),
                        parameters = MeetingParameters(
                                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                minDuration = Duration.standardHours(5),
                                minParticipants = 3
                        ),
                        manager = ParticipantId.participantId("master"),
                        participants = setOf(
                                Participant(
                                        participantId = ParticipantId.participantId("321"),
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
    }

    @Test
    fun canSerializeSchedulingJob() {
        assertThat(objectMapper.writeValueAsString(SchedulingJob(
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
                                participantId = ParticipantId.participantId("321"),
                                name = "Greg",
                                importance = 1,
                                availability = setOf(
                                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"))
                                )
                        )
                )
        ))
        ).isEqualTo("{\"id\":{\"id\":\"933\"},\"algorithm\":{\"type\":\"intervalProjection\"},\"parameters\":{\"between\":\"1507040100000-1507046400000\",\"minDuration\":18000000,\"minParticipants\":3},\"participants\":[{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":1,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1}]}]}")
    }

}