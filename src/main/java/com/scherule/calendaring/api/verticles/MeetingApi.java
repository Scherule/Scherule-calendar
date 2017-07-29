package com.scherule.calendaring.api.verticles;

import com.scherule.calendaring.domain.Meeting;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface MeetingApi {

    void createMeeting(Meeting meeting, Handler<AsyncResult<Void>> handler);

    void getMeeting(String meetingId, Handler<AsyncResult<Void>> handler);

    void removeMeeting(Long meetingId, Handler<AsyncResult<Void>> handler);

    void updateMeeting(Meeting meeting, Handler<AsyncResult<Void>> handler);

}
