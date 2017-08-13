package com.scherule.scheduling.converters

import cucumber.api.Transformer
import org.joda.time.Interval

class IntervalConverter : Transformer<Interval>() {

    override fun transform(value: String): Interval {
        return Interval.parse(value)
    }

}