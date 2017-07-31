package com.scherule.calendaring.api.verticles;

import com.scherule.calendaring.api.MainApiException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class SwaggerApiVerticle extends AbstractVerticle {

    final static Logger LOGGER = LoggerFactory.getLogger(SwaggerApiVerticle.class);

    final static String GET_SWAGGER = "getSwagger";

    @Override
    public void start() throws Exception {
        FileSystem vertxFileSystem = vertx.fileSystem();

        vertxFileSystem.readFile("swagger.json", swaggerFile -> {
            final JsonObject swaggerDefinition = new JsonObject(swaggerFile.result().toString());
            if (swaggerFile.succeeded()) {
                vertx.eventBus().<JsonObject>consumer(GET_SWAGGER).handler(message -> {
                    try {
                        message.reply(Json.encodePrettily(swaggerDefinition));
                    } catch (Exception e) {
                        logUnexpectedError("getMeeting", e);
                        message.fail(MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(), MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage());
                    }
                });
            }
        });

    }

    private void logUnexpectedError(String serviceName, Throwable cause) {
        LOGGER.error("Unexpected error in " + serviceName, cause);
    }

}

