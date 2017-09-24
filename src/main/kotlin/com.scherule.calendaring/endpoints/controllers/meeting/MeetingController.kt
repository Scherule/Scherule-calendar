package com.scherule.calendaring.endpoints.controllers.meeting

import com.scherule.calendaring.domain.entities.Meeting
import com.scherule.calendaring.domain.entities.MeetingId
import com.scherule.calendaring.domain.services.MeetingService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.ws.rs.PathParam

@Api(value = "meeting", description = "Meeting operations", tags = arrayOf("meeting"))
@RestController
@RequestMapping("/account")
class MeetingController
@Autowired constructor(
        private val meetingService: MeetingService
) {

    @ApiOperation(value = "Obtain meeting", response = Meeting::class)
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    fun getMeeting(@PathParam("id") id: String) = meetingService.getMeeting(MeetingId.meetingId(id))

    @ApiOperation(value = "Create meeting", response = Meeting::class)
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.CREATED)
    fun postMeeting(@RequestBody meeting: Meeting) = meetingService.createMeeting(meeting)

}