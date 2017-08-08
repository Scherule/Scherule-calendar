package com.scherule.calendaring;

import com.intapp.vertx.guice.GuiceVertxDeploymentManager;
import com.scherule.calendaring.verticles.SchedulingHandlingVerticle;
import com.scherule.calendaring.verticles.SchedulingRequestingVerticle;
import com.scherule.calendaring.verticles.WebControllerVerticle;
import com.scherule.commons.MicroServiceVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// https://github.com/vert-x3/vertx-examples/blob/master/web-examples/src/main/java/io/vertx/example/web/rest/SimpleREST.java
@SuppressWarnings("unused")
public class CalendaringRootVerticle extends MicroServiceVerticle {

    private static final Logger log = LoggerFactory.getLogger(CalendaringRootVerticle.class);

    @Override
    public void start(Future<Void> startFuture) {
        super.start(startFuture);

        GuiceVertxDeploymentManager deploymentManager = new GuiceVertxDeploymentManager(vertx);
        deploymentManager.deployVerticle(WebControllerVerticle.class);
        deploymentManager.deployVerticle(SchedulingRequestingVerticle.class);
        deploymentManager.deployVerticle(SchedulingHandlingVerticle.class);
    }

}