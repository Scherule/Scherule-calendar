package com.scherule.calendaring.domain

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.scherule.calendaring.CalendaringApplicationContext
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.TestExtensionContext

class InjectorExtension(
        modules: List<Module> = listOf(CalendaringApplicationContext())
) : BeforeTestExecutionCallback {

    val injector: Injector = Guice.createInjector(modules)

    override fun beforeTestExecution(context: TestExtensionContext?) {
        injector.injectMembers(context?.testInstance)
    }

}