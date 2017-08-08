package com.scherule.calendaring.verticles;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.rabbitmq.client.Channel;
import com.scherule.calendaring.SchedulingResultsConsumer;
import com.scherule.commons.MicroServiceVerticle;
import io.vertx.core.Future;

import java.io.IOException;

public class SchedulingRequestingVerticle extends MicroServiceVerticle {

    @Inject
    @Named("scheduling.channel")
    private Channel schedulingChannel;

    @Inject
    private SchedulingResultsConsumer schedulingResultsConsumer;

    @Override
    public void start(Future<Void> startFuture) {
        try {
            schedulingChannel.queueDeclare("scheduling-queue-results", true, false, false, null);
            schedulingChannel.basicConsume("scheduling-queue-results", false, schedulingResultsConsumer);
        } catch (IOException e) {
            startFuture.fail(e);
        }
        super.start(startFuture);
    }

}
