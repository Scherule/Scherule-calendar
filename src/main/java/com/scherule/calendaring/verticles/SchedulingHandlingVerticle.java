package com.scherule.calendaring.verticles;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.rabbitmq.client.Channel;
import com.scherule.calendaring.CalendaringRootVerticle;
import com.scherule.calendaring.SchedulingJobDispatcher;
import com.scherule.commons.MicroServiceVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SchedulingHandlingVerticle extends MicroServiceVerticle {

    private static final Logger log = LoggerFactory.getLogger(CalendaringRootVerticle.class);

    private final Channel schedulingChannel;

    private final SchedulingJobDispatcher schedulingJobDispatcher;

    @Inject
    public SchedulingHandlingVerticle(
            @Named("scheduling.channel") Channel schedulingChannel,
            SchedulingJobDispatcher schedulingJobDispatcher
    ) {
        this.schedulingChannel = schedulingChannel;
        this.schedulingJobDispatcher = schedulingJobDispatcher;
    }

    @Override
    public void start(Future<Void> startFuture) {
        super.start(startFuture);
        try {
            schedulingChannel.queueDeclare("scheduling-queue", true, false, false, null);
            schedulingJobDispatcher.dispatchJob();
        } catch (IOException e) {
            throw new IllegalStateException("Could not bind scheduling job consumer to scheduling channel", e);
        }
    }
}
