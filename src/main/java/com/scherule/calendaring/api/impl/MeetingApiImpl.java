package com.scherule.calendaring.api.impl;

import com.scherule.calendaring.api.verticles.MeetingApi;
import com.scherule.calendaring.domain.Meeting;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by kbhit on 29.07.2017.
 */
public class MeetingApiImpl implements MeetingApi {
    @Override
    public void createMeeting(Meeting meeting, Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void getMeeting(String meetingId, Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void removeMeeting(Long meetingId, Handler<AsyncResult<Void>> handler) {

    }

    @Override
    public void updateMeeting(Meeting meeting, Handler<AsyncResult<Void>> handler) {

    }
}
