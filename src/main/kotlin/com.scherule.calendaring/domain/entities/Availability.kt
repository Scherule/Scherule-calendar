package com.scherule.calendaring.domain.entities

import org.hibernate.annotations.Columns
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.joda.time.Interval
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AVAILABILITY")
class Availability(

        @Type(type = "org.joda.time.contrib.hibernate.PersistentInterval")
        @Columns(
                columns = arrayOf(
                        Column(name = "FROM"),
                        Column(name = "TO")
                )
        )
        @NotNull
        val interval: Interval,

        @Column(name = "PREFERENCE")
        @NotNull
        @Min(0)
        @Max(100)
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


    override fun toString(): String {
        return "Availability(interval=$interval, preference=$preference)"
    }

}