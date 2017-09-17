package com.scherule.calendaring.configuration

import com.scherule.calendaring.endpoints.messaging.SchedulingResultsConsumer
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.springframework.jms.listener.adapter.MessageListenerAdapter
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageType
import javax.inject.Named
import javax.jms.ConnectionFactory
import javax.jms.Destination


@Configuration
class CalendaringQueueConfiguration {

    @Bean
    @Qualifier("scheduling.request.destination")
    fun schedulingRequestDestination(
            @Value("scheduling.request.destination") requestDestination: String
    ) = ActiveMQQueue(requestDestination)

    @Bean
    @Qualifier("scheduling.response.destination")
    fun schedulingResponseDestination(
            @Value("scheduling.response.destination") responseDestination: String
    ) = ActiveMQQueue(responseDestination)

    @Bean
    fun schedulingResponseContainer(
            @Value("scheduling.response.destination") destination: Destination,
            @Named("scheduling.response.adapter") adapter: MessageListenerAdapter,
            connectionFactory: ConnectionFactory
    ): DefaultMessageListenerContainer = DefaultMessageListenerContainer().apply {
        setConnectionFactory(connectionFactory)
        setDestination(destination)
        setupMessageListener(adapter)
    }

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

}