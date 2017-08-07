package com.scherule.calendaring.api.verticles

import com.scherule.calendaring.domain.Meeting
import rx.Completable
import rx.Single

interface MeetingApi {

    fun createMeeting(meeting: Meeting): Single<Meeting>

    fun getMeeting(meetingId: String): Single<Meeting>

    fun removeMeeting(meetingId: Long): Completable

    fun updateMeeting(meeting: Meeting): Single<Meeting>

}
