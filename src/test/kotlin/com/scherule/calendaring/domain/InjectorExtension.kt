package com.scherule.calendaring.domain

import com.google.inject.Guice
import com.google.inject.Injector
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.reflect.full.createInstance

class InjectorExtension : BeforeAllCallback, BeforeTestExecutionCallback {

    lateinit var injector: Injector

    override fun beforeAll(context: ExtensionContext) {
        val testClass = context.testClass.get()
        val injectorContext: InjectorContext? = testClass.getAnnotation(InjectorContext::class.java)
        if (injectorContext != null) {
            injector = Guice.createInjector(injectorContext.modules.map {
                it.createInstance()
            })
        } else {
            throw IllegalStateException("There must be an InjectorContext annotation put on the test class")
        }
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        injector.injectMembers(context.testInstance)
    }

}