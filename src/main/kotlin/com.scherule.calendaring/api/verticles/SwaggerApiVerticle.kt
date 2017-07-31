package com.scherule.calendaring.api.verticles

import com.scherule.calendaring.api.MainApiException
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory

class SwaggerApiVerticle : AbstractVerticle() {

    companion object {

        internal val LOGGER = LoggerFactory.getLogger(SwaggerApiVerticle::class.java)

        internal val GET_SWAGGER = "getSwagger"

    }

    @Throws(Exception::class)
    override fun start() {
        val vertxFileSystem = vertx.fileSystem()

        vertxFileSystem.readFile("swagger.json") { swaggerFile ->
            val swaggerDefinition = JsonObject(swaggerFile.result().toString())
            if (swaggerFile.succeeded()) {
                vertx.eventBus().consumer<JsonObject>(GET_SWAGGER).handler { message ->
                    try {
                        message.reply(Json.encodePrettily(swaggerDefinition))
                    } catch (e: Exception) {
                        logUnexpectedError("getMeeting", e)
                        message.fail(MainApiException.INTERNAL_SERVER_ERROR.statusCode, MainApiException.INTERNAL_SERVER_ERROR.statusMessage)
                    }
                }
            }
        }

    }

    private fun logUnexpectedError(serviceName: String, cause: Throwable) {
        LOGGER.error("Unexpected error in " + serviceName, cause)
    }

}

