package com.scherule.calendaring

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.Channel
import com.scherule.calendaring.domain.*
import org.joda.time.Duration
import org.joda.time.Interval
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class SchedulingJobDispatcher
@Inject constructor(
        @Named("scheduling.channel") private val channel: Channel,
        private val objectMapper: ObjectMapper
) {

    companion object {
        val log = LoggerFactory.getLogger(SchedulingJobDispatcher::class.java)
    }

    fun dispatchJob() {
        val participant = Participant(
                id = ParticipantId("1"),
                name = "Greg",
                importance = 100,
                availability = setOf(
                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"))
                )
        )



        channel.basicPublish("", "scheduling-queue", null, objectMapper.writeValueAsBytes(
                SchedulingJob(
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
                )
        ))
    }

}