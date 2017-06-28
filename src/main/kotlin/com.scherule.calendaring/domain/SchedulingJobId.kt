package com.scherule.calendaring.domain

class SchedulingJobId(val id: String) {

    companion object {
        fun schedulingJobId(id: String) : SchedulingJobId {
            return SchedulingJobId(id)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as SchedulingJobId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "SchedulingJobId(id='$id')"
    }

}