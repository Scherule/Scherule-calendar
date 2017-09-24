package com.scherule.calendaring.endpoints.messaging

import com.scherule.calendaring.endpoints.messaging.scheduling.SchedulingResultsConsumer
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.springframework.jms.listener.adapter.MessageListenerAdapter
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageType
import javax.inject.Named
import javax.jms.ConnectionFactory
import javax.jms.Destination


@Configuration
@EnableJms
class messaging {

    @Bean
    @Qualifier("scheduling.request.destination")
    fun schedulingRequestDestination(
            @Value("app.scheduling.request.destination.name") requestDestination: String
    ) = ActiveMQQueue(requestDestination)

    @Bean
    @Qualifier("scheduling.response.destination")
    fun schedulingResponseDestination(
            @Value("app.scheduling.response.destination.name") responseDestination: String
    ) = ActiveMQQueue(responseDestination)

    @Bean
    @Qualifier("scheduling.response.adapter")
    fun schedulingResponseMessageListenerAdapter(
            consumer: SchedulingResultsConsumer
    ) = MessageListenerAdapter(consumer).apply {
        setDefaultListenerMethod("handleSchedulingResults")
        setMessageConverter(
                MappingJackson2MessageConverter().apply {
                    setTargetType(MessageType.TEXT)
                    setTypeIdPropertyName("_type")
                }
        )
    }

    @Bean
    fun schedulingResponseContainer(
            @Named("scheduling.response.destination") destination: Destination,
            @Named("scheduling.response.adapter") adapter: MessageListenerAdapter,
            connectionFactory: ConnectionFactory
    ): DefaultMessageListenerContainer = DefaultMessageListenerContainer().apply {
        setConnectionFactory(connectionFactory)
        setDestination(destination)
        setupMessageListener(adapter)
    }

}