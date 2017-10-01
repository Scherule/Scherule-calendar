package com.scherule.calendaring.domain.entities

import org.hibernate.annotations.GenericGenerator
import org.threeten.extra.Interval
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AVAILABILITY")
class Availability(

        @NotNull
        val interval: Interval,

        @NotNull
        @Min(0)
        @Max(100)
        @Column(name = "PREFERENCE")
        val preference: Int

) {

    companion object {

        fun availableIn(interval: Interval, preference: Int = 1): Availability {
            return Availability(interval, preference);
        }

    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private val id: String? = null


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