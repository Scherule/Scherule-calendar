package com.scherule.calendaring.domain.repositories

import com.scherule.calendaring.domain.entities.Meeting
import com.scherule.calendaring.domain.entities.MeetingId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository : CrudRepository<Meeting, MeetingId>