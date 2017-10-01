package com.scherule.calendaring.domain.entities

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "PARTICIPANT")
class Participant(

        @EmbeddedId
        val participantId: ParticipantId,

        @Column(name = "NAME")
        @Length(min = 4, max = 100)
        @NotNull
        val name: String,

        @Column(name = "IMPORTANCE")
        @NotNull
        @Min(0)
        @Max(100)
        val importance: Int,

        @OneToMany(mappedBy = "id")
        @Size(max = 10)
        val availability: Set<Availability>

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Participant

        if (participantId != other.participantId) return false

        return true
    }

    override fun hashCode(): Int {
        return participantId.hashCode()
    }


}