package com.scherule.scheduling.converters

import cucumber.api.Transformer
import org.joda.time.Duration


class DurationConverter : Transformer<Duration>() {

    override fun transform(value: String): Duration {
        return Duration.standardHours(value.toLong())
    }

}