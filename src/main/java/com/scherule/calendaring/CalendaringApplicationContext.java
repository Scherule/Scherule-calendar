package com.scherule.calendaring;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scherule.calendaring.domain.services.ServicesModule;
import com.scherule.calendaring.modules.CalendaringDomainModule;
import com.scherule.calendaring.modules.CalendaringQueueModule;
import com.scherule.calendaring.modules.PersistenceModule;
import com.scherule.calendaring.api.verticles.VerticlesModule;


class CalendaringApplicationContext {

    private final Injector injector = Guice.createInjector(
            new ServicesModule(),
            new VerticlesModule(),
            new CalendaringQueueModule(),
            new PersistenceModule(),
            new CalendaringDomainModule()
    );

    Injector getInjector() {
        return injector;
    }

}
