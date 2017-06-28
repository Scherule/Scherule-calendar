package com.scherule.calendaring

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.Channel
import com.scherule.calendaring.domain.Participant
import com.scherule.calendaring.domain.ParticipantId
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
                availability = setOf(
                        Interval.parse("2012-01-01T14:15Z/2014-06-20T16:00Z"),
                        Interval.parse("2012-01-01T14:15Z/2014-06-20T16:00Z")
                )
        )


        channel.basicPublish("", "scheduling-queue", null, "Hi hi hi!".toByteArray())
    }

}