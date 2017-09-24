package com.scherule.calendaring.serialization

import com.scherule.calendaring.domain.entities.*
import com.scherule.calendaring.endpoints.messaging.scheduling.SchedulingJob
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
class SchedulingJobSerializationTests {

    @Autowired
    lateinit var schedulingJobIdTester: JacksonTester<SchedulingJobId>

    @Autowired
    lateinit var schedulingJobTester: JacksonTester<SchedulingJob>

    @Test
    fun canSerializeSchedulingJobId() {
        assertThat(schedulingJobIdTester.write(SchedulingJobId.schedulingJobId("123")))
                .isEqualTo("""{"id":"123"}""")
    }

    @Test
    fun canSerializeSchedulingJob() {
        assertThat(schedulingJobTester.write(SchedulingJob(
                schedulingJobId = SchedulingJobId.schedulingJobId("933"),
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
        ).isEqualTo("""{"schedulingJobId":{"id":"933"},"algorithm":{"type":"intervalProjection"},"parameters":{"between":"1507040100000-1507046400000","minDuration":18000000,"minParticipants":3},"participants":[{"participantId":{"id":"321"},"name":"Greg","importance":1,"availability":[{"interval":"1507040100000-1507046400000","preference":1}]}]}""")
    }

}