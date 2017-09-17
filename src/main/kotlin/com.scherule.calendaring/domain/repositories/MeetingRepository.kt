package com.scherule.calendaring.domain.repositories

import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.MeetingId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository : CrudRepository<Meeting, MeetingId>