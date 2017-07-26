package com.scherule.calendaring

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SchedulingResultsConsumer
@Inject constructor(
        @Named("scheduling.channel") channel: Channel
) : DefaultConsumer(channel) {

    companion object {
        val log = LoggerFactory.getLogger(SchedulingResultsConsumer::class.java)!!
    }

    override fun handleDelivery(
            consumerTag: String,
            envelope: Envelope,
            properties: AMQP.BasicProperties,
            body: ByteArray
    ) {
        log.debug("Processing scheduling request ${body.toString(Charset.defaultCharset())}")
        channel.basicAck(envelope.deliveryTag, false)
    }

}