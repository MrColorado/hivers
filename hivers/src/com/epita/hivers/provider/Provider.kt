package com.epita.hivers.provider

import java.lang.reflect.Method
import java.util.*

interface Provider<BEAN_TYPE> {
    fun provideOptional() : Optional<BEAN_TYPE> {
        return Optional.ofNullable(provide())
    }
    fun provide() : BEAN_TYPE?
    fun providesForClass() : Class<BEAN_TYPE>

    fun before(method: Method, lambda: () -> Unit) : Any {
        return provideOptional()
    }
    fun after(method: Method, lambda: () -> Unit) : Any {
        return provideOptional()
    }
    fun around(method: Method, lambda: () -> Unit) : Any {
        return provideOptional()
    }
}