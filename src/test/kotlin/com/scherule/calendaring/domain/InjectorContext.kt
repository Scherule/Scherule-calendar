package com.scherule.calendaring.domain

import com.google.inject.Module
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class InjectorContext(vararg val modules: KClass<out Module>)