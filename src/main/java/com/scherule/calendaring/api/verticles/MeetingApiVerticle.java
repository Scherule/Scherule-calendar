package com.scherule.calendaring.api.verticles;

import com.scherule.calendaring.api.MainApiException;
import com.scherule.calendaring.api.impl.MeetingApiImpl;
import com.scherule.calendaring.domain.Meeting;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MeetingApiVerticle extends AbstractVerticle {
    final static Logger LOGGER = LoggerFactory.getLogger(MeetingApiVerticle.class);

    final static String CREATEMEETING_SERVICE_ID = "createMeeting";
    final static String GETMEETING_SERVICE_ID = "getMeeting";
    final static String REMOVEMEETING_SERVICE_ID = "removeMeeting";
    final static String UPDATEMEETING_SERVICE_ID = "updateMeeting";

    MeetingApi service = new MeetingApiImpl();

    @Override
    public void start() throws Exception {

        //Consumer for createMeeting
        vertx.eventBus().<JsonObject>consumer(CREATEMEETING_SERVICE_ID).handler(message -> {
            try {
                Meeting body = Json.mapper.readValue(message.body().getJsonObject("body").encode(), Meeting.class);
                service.createMeeting(body, result -> {
                    if (result.succeeded())
                        message.reply(null);
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, "createMeeting");
                    }
                });
            } catch (Exception e) {
                logUnexpectedError("createMeeting", e);
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(), MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage());
            }
        });

        //Consumer for getMeeting
        vertx.eventBus().<JsonObject>consumer(GETMEETING_SERVICE_ID).handler(message -> {
            try {
                String meetingId = message.body().getString("meetingId");
                service.getMeeting(meetingId, result -> {
                    if (result.succeeded())
                        message.reply(null);
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, "getMeeting");
                    }
                });
            } catch (Exception e) {
                logUnexpectedError("getMeeting", e);
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(), MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage());
            }
        });

        //Consumer for removeMeeting
        vertx.eventBus().<JsonObject>consumer(REMOVEMEETING_SERVICE_ID).handler(message -> {
            try {
                Long meetingId = Json.mapper.readValue(message.body().getString("meetingId"), Long.class);
                service.removeMeeting(meetingId, result -> {
                    if (result.succeeded())
                        message.reply(null);
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, "removeMeeting");
                    }
                });
            } catch (Exception e) {
                logUnexpectedError("removeMeeting", e);
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(), MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage());
            }
        });

        //Consumer for updateMeeting
        vertx.eventBus().<JsonObject>consumer(UPDATEMEETING_SERVICE_ID).handler(message -> {
            try {
                Meeting body = Json.mapper.readValue(message.body().getJsonObject("body").encode(), Meeting.class);
                service.updateMeeting(body, result -> {
                    if (result.succeeded())
                        message.reply(null);
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, "updateMeeting");
                    }
                });
            } catch (Exception e) {
                logUnexpectedError("updateMeeting", e);
                message.fail(MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(), MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage());
            }
        });

    }

    private void manageError(Message<JsonObject> message, Throwable cause, String serviceName) {
        int code = MainApiException.INTERNAL_SERVER_ERROR.getStatusCode();
        String statusMessage = MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
        if (cause instanceof MainApiException) {
            code = ((MainApiException) cause).getStatusCode();
            statusMessage = ((MainApiException) cause).getStatusMessage();
        } else {
            logUnexpectedError(serviceName, cause);
        }

        message.fail(code, statusMessage);
    }

    private void logUnexpectedError(String serviceName, Throwable cause) {
        LOGGER.error("Unexpected error in " + serviceName, cause);
    }

}
