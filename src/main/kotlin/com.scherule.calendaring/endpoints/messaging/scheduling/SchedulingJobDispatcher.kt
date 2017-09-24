package com.scherule.calendaring.endpoints.messaging.scheduling

import com.scherule.calendaring.domain.entities.*
import org.joda.time.Duration
import org.joda.time.Interval
import org.slf4j.LoggerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component
import javax.inject.Inject
import javax.inject.Named
import javax.jms.Destination


@Component
class SchedulingJobDispatcher
@Inject constructor(
        @Named("scheduling.request.destination") private val requestDestination: Destination,
        @Named("scheduling.response.destination") private val responseDestination: Destination,
        private val jmsTemplate: JmsTemplate
) {

    companion object {
        val log = LoggerFactory.getLogger(SchedulingJobDispatcher::class.java)
    }

    fun dispatchJob() {
        jmsTemplate.convertAndSend(requestDestination, SchedulingJob(
                schedulingJobId = SchedulingJobId.schedulingJobId("933"),
                algorithm = SchedulingAlgorithm(
                        type = "interval-projection"
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
        ), {
            it.apply {
                jmsReplyTo = responseDestination
                jmsCorrelationID = "1"
            }
        })
    }

}