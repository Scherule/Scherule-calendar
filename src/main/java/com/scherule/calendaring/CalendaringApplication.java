package com.scherule.calendaring;

import com.scherule.commons.MicroServiceLauncher;

class CalendaringApplication extends MicroServiceLauncher {

    public static final CalendaringApplicationContext context = new CalendaringApplicationContext();

    public static void main(String[] args) {
        new CalendaringApplication().dispatch(args);
    }

}