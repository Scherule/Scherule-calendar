package com.scherule.calendaring.api.verticles

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Singleton
import com.scherule.calendaring.api.MainApiException
import com.scherule.calendaring.domain.Meeting
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.rxjava.core.AbstractVerticle
import io.vertx.rxjava.core.eventbus.Message
import rx.Observable
import javax.inject.Inject

@Singleton
class MeetingApiVerticle
@Inject constructor(
        private val objectMapper: ObjectMapper,
        private val meetingService: MeetingApi
) : AbstractVerticle() {

    companion object {
        internal val LOGGER = LoggerFactory.getLogger(MeetingApiVerticle::class.java)

        internal val CREATEMEETING_SERVICE_ID = "createMeeting"
        internal val GETMEETING_SERVICE_ID = "getMeeting"
        internal val REMOVEMEETING_SERVICE_ID = "removeMeeting"
        internal val UPDATEMEETING_SERVICE_ID = "updateMeeting"
    }

    @Throws(Exception::class)
    override fun start() {


        vertx.eventBus().consumer<JsonObject>(CREATEMEETING_SERVICE_ID).toObservable()
                .subscribe { message ->
                    Observable.just(objectMapper.readValue(message.body().getJsonObject("body").encode(), Meeting::class.java))
                            .flatMap(meetingService::createMeeting)
                            .subscribe(
                                    { message.reply(Json.encodePrettily(it)) },
                                    { manageError(message, it, "createMeeting") }
                            )
                }

//
//        vertx.eventBus().consumer<JsonObject>(CREATEMEETING_SERVICE_ID).toObservable()
//                .map { objectMapper.readValue(it.body().getJsonObject("body").encode(), Meeting::class.java) }
//                .flatMap(meetingService::createMeeting)
//                .subscribe(
//                        { Json.encodePrettily(it) },
//                        { throw it }
//                )

        //Consumer for getMeeting
        vertx.eventBus().consumer<JsonObject>(GETMEETING_SERVICE_ID).handler { message ->
            try {
                val meetingId = message.body().getString("meetingId")
                meetingService.getMeeting(meetingId, Handler<AsyncResult<Meeting>> { result ->
                    if (result.succeeded())
                        message.reply(Json.encodePrettily(result.result()))
                    else {
                        val cause = result.cause()
                        manageError(message, cause, "getMeeting")
                    }
                })
            } catch (e: Exception) {
                logUnexpectedError("getMeeting", e)
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.statusCode, MainApiException.INTERNAL_SERVER_ERROR.statusMessage)
            }
        }

        //Consumer for removeMeeting
        vertx.eventBus().consumer<JsonObject>(REMOVEMEETING_SERVICE_ID).handler { message ->
            try {
                val meetingId = Json.mapper.readValue(message.body().getString("meetingId"), Long::class.java)
                meetingService.removeMeeting(meetingId, Handler<AsyncResult<Void>> { result ->
                    if (result.succeeded())
                        message.reply(Json.encodePrettily(result.result()))
                    else {
                        val cause = result.cause()
                        manageError(message, cause, "removeMeeting")
                    }
                })
            } catch (e: Exception) {
                logUnexpectedError("removeMeeting", e)
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.statusCode, MainApiException.INTERNAL_SERVER_ERROR.statusMessage)
            }
        }

        //Consumer for updateMeeting
        vertx.eventBus().consumer<JsonObject>(UPDATEMEETING_SERVICE_ID).handler { message ->
            try {
                val body = Json.mapper.readValue(message.body().getJsonObject("body").encode(), Meeting::class.java)
                meetingService.updateMeeting(body, Handler<AsyncResult<Meeting>> { result ->
                    if (result.succeeded())
                        message.reply(Json.encodePrettily(result.result()))
                    else {
                        val cause = result.cause()
                        manageError(message, cause, "updateMeeting")
                    }
                })
            } catch (e: Exception) {
                logUnexpectedError("updateMeeting", e)
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.statusCode, MainApiException.INTERNAL_SERVER_ERROR.statusMessage)
            }
        }

    }

    private fun manageError(message: Message<JsonObject>, cause: Throwable, serviceName: String) {
        var code = MainApiException.INTERNAL_SERVER_ERROR.statusCode
        var statusMessage = MainApiException.INTERNAL_SERVER_ERROR.statusMessage
        if (cause is MainApiException) {
            code = cause.statusCode
            statusMessage = cause.statusMessage
        } else {
            logUnexpectedError(serviceName, cause)
        }

        message.fail(code, statusMessage)
    }

    private fun logUnexpectedError(serviceName: String, cause: Throwable) {
        LOGGER.error("Unexpected error in " + serviceName, cause)
    }

}
