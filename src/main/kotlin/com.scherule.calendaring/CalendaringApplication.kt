package com.scherule.calendaring

import com.scherule.commons.MicroServiceLauncher

class CalendaringApplication : MicroServiceLauncher() {

    companion object {
        fun main(args: Array<String>) {
            CalendaringApplication().dispatch(args)
        }
    }

}