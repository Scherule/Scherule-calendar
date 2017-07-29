package com.scherule.calendaring.api.verticles;

import com.scherule.calendaring.api.MainApiException;

public final class MeetingApiException extends MainApiException {

    public MeetingApiException(int statusCode, String statusMessage) {
        super(statusCode, statusMessage);
    }
    
    public static final MeetingApiException Meeting_createMeeting_401_Exception = new MeetingApiException(401, "Created");
    public static final MeetingApiException Meeting_createMeeting_405_Exception = new MeetingApiException(405, "Validation exception");
    public static final MeetingApiException Meeting_getMeeting_400_Exception = new MeetingApiException(400, "Invalid ID supplied");
    public static final MeetingApiException Meeting_getMeeting_404_Exception = new MeetingApiException(404, "Meeting not found");
    public static final MeetingApiException Meeting_removeMeeting_400_Exception = new MeetingApiException(400, "Invalid ID supplied");
    public static final MeetingApiException Meeting_removeMeeting_404_Exception = new MeetingApiException(404, "Meeting not found");
    public static final MeetingApiException Meeting_updateMeeting_404_Exception = new MeetingApiException(404, "Meeting not found");
    public static final MeetingApiException Meeting_updateMeeting_405_Exception = new MeetingApiException(405, "Validation exception");
    

}