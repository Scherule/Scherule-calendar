package com.scherule.calendaring.behavioral

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Stage
import com.scherule.calendaring.CalendaringApplication.APPLICATION_MODULES
import cucumber.api.guice.CucumberModules
import cucumber.runtime.java.guice.InjectorSource

class CucumberInjectorSource : InjectorSource {

    override fun getInjector(): Injector {
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, *APPLICATION_MODULES.toTypedArray());
    }

}