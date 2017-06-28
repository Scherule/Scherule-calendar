package com.scherule.calendaring

import com.rabbitmq.client.Channel
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class SchedulingJobDispatcher
@Inject constructor(
        @Named("scheduling.channel") private val channel: Channel
) {

    companion object {
        val log = LoggerFactory.getLogger(SchedulingJobDispatcher::class.java)
    }

    fun dispatchJob() {
        channel.basicPublish("", "scheduling-queue", null, "Hi hi hi!".toByteArray())
    }

}