package com.scherule.calendaring.repositories

import com.scherule.calendaring.domain.entities.*
import com.scherule.calendaring.domain.repositories.MeetingRepository
import org.joda.time.Duration
import org.joda.time.Interval
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired


@RunWith(SpringRunner::class)
@DataJpaTest
class MeetingRepositoryTest {

    @Autowired
    private lateinit var repository: MeetingRepository

    @Test
    fun canSaveMeeting() {
        assertThat(repository.save(Meeting(
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
        )).id).isNotNull()
    }

}