package com.scherule.calendaring;

import com.scherule.commons.MicroServiceLauncher;

class CalendaringApplication extends MicroServiceLauncher {

    public static void main(String[] args) {
        new CalendaringApplication().dispatch(args);
    }

}