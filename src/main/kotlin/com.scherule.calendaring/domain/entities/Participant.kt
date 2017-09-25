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

        @get:EmbeddedId
        val participantId: ParticipantId,

        @get:Column(name = "NAME")
        @get:Length(min = 4, max = 100)
        @get:NotNull
        val name: String,

        @get:Column(name = "IMPORTANCE")
        @get:NotNull
        @get:Min(0)
        @get:Max(100)
        val importance: Int,

        @get:OneToMany(mappedBy = "id")
        @get:Size(max = 10)
        val availability: Set<Availability>

) {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "participant_seq", sequenceName = "participant_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_seq")
    val id: Long? = null

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