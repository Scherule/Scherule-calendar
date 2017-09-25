package com.scherule.calendaring.domain.entities

import org.joda.time.Interval
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AVAILABILITIES")
class Availability(
        @get:NotNull
        val interval: Interval,
        @get:NotNull
        @get:Min(0)
        @get:Max(100)
        val preference: Int
) {

    companion object {

        fun availableIn(interval: Interval, preference: Int = 1): Availability {
            return Availability(interval, preference);
        }

    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "availability_seq", sequenceName = "availability_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_seq")
    val id: Long? = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Availability

        if (interval != other.interval) return false
        if (preference != other.preference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = interval.hashCode()
        result = 31 * result + preference
        return result
    }

    override fun toString(): String {
        return "Availability(interval=$interval, preference=$preference)"
    }


}