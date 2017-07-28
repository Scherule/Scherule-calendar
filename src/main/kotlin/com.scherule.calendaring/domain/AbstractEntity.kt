package com.scherule.calendaring.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(NON_NULL)
@JsonIgnoreProperties("id", "revision")
abstract class AbstractEntity {

    @JsonProperty("_id")
    val id: String? = null

    @JsonProperty("_rev")
    val revision: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as AbstractEntity

        if (id != other.id) return false
        if (revision != other.revision) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (revision?.hashCode() ?: 0)
        return result
    }

}