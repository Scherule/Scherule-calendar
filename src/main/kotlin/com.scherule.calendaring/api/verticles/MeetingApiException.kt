package com.scherule.calendaring.api.verticles

import com.scherule.calendaring.api.MainApiException

class MeetingApiException(statusCode: Int, statusMessage: String)
    : MainApiException(statusCode, statusMessage) {

    companion object {
        val Meeting_createMeeting_401_Exception = MeetingApiException(401, "Created")
        val Meeting_createMeeting_405_Exception = MeetingApiException(405, "Validation exception")
        val Meeting_getMeeting_400_Exception = MeetingApiException(400, "Invalid ID supplied")
        val Meeting_getMeeting_404_Exception = MeetingApiException(404, "Meeting not found")
        val Meeting_removeMeeting_400_Exception = MeetingApiException(400, "Invalid ID supplied")
        val Meeting_removeMeeting_404_Exception = MeetingApiException(404, "Meeting not found")
        val Meeting_updateMeeting_404_Exception = MeetingApiException(404, "Meeting not found")
        val Meeting_updateMeeting_405_Exception = MeetingApiException(405, "Validation exception")
    }

}