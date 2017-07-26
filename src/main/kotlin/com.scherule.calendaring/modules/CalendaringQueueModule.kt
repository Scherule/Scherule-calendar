package com.scherule.calendaring.modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names.named
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory


class CalendaringQueueModule : AbstractModule() {

    override fun configure() {
        val factory = ConnectionFactory()
        factory.host = "localhost"
        factory.port = 5672
        try {
            val connection = factory.newConnection()
            val channel = connection.createChannel()
            bind(Channel::class.java).annotatedWith(named("scheduling.channel")).toInstance(channel)
        } catch (e: Exception) {
            throw IllegalStateException("Could not connect to scheduling channel", e)
        }

    }

}