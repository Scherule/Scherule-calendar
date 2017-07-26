package com.scherule.calendaring;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scherule.calendaring.modules.CalendaringDomainModule;
import com.scherule.calendaring.modules.CalendaringQueueModule;


class CalendaringApplicationContext {

    private final Injector injector = Guice.createInjector(
            new CalendaringQueueModule(),
            new CalendaringDomainModule()
    );

    Injector getInjector() {
        return injector;
    }

}
