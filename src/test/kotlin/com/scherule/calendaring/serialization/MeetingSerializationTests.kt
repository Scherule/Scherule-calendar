package com.scherule.calendaring.serialization


import com.scherule.calendaring.domain.entities.*
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Duration
import org.joda.time.Interval
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@JsonTest
class MeetingSerializationTests {

    @Autowired
    lateinit var meetingIdTester: JacksonTester<MeetingId>

    @Autowired
    lateinit var meetingParametersTester: JacksonTester<MeetingParameters>

    @Autowired
    lateinit var meetingTester: JacksonTester<Meeting>

    @Test
    fun canSerializeMeetingId() {
        assertThat(meetingIdTester.write(MeetingId.meetingId("123")))
                .isEqualTo("""{"id":"123"}""")
    }

    @Test
    fun canSerializeMeetingParameters() {
        assertThat(meetingParametersTester.write(MeetingParameters(
                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                minDuration = Duration.standardHours(5),
                minParticipants = 3
        ))).isEqualTo("""{"between":"1507040100000-1507046400000","minDuration":18000000,"minParticipants":3}""")
    }

    @Test
    fun canSerializeMeeting() {
        assertThat(meetingTester.write(Meeting(
                meetingId = MeetingId.meetingId("123"),
                parameters = MeetingParameters(
                        between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                        minDuration = Duration.standardHours(5),
                        minParticipants = 3
                ),
                organizer = Organizer(ParticipantId.participantId("321")),
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
        ))).isEqualTo("""{"meetingId":{"id":"123"},"parameters":{"between":"1507040100000-1507046400000","minDuration":18000000,"minParticipants":3},"manager":{"id":"master"},"participants":[{"participantId":{"id":"321"},"name":"Greg","importance":100,"availability":[{"interval":"1507040100000-1507046400000","preference":1},{"interval":"1507108500000-1507158000000","preference":1}]}],"keychain":{"managementKey":{"owner":{"id":"abc"},"hash":""},"participationKeys":{}},"meetingState":"CREATED"}""")
    }

    @Test
    fun canDeserializeMeeting() {
        assertThat(meetingTester.parse("""{"meetingId":{"id":"123"},"parameters":{"between":"1507040100000-1507046400000","minDuration":18000000,"minParticipants":3},"manager":{"id":"master"},"participants":[{"participantId":{"id":"321"},"name":"Greg","importance":100,"availability":[{"interval":"1507040100000-1507046400000","preference":1},{"interval":"1507108500000-1507158000000","preference":1}]}],"keychain":{"managementKey":{"owner":{"id":"abc"},"hash":""},"participationKeys":{}},"meetingState":"CREATED"}"""))
                .isEqualTo(Meeting(
                        meetingId = MeetingId.meetingId("123"),
                        parameters = MeetingParameters(
                                between = Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"),
                                minDuration = Duration.standardHours(5),
                                minParticipants = 3
                        ),
                        organizer = Organizer(ParticipantId.participantId("321")),
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

}