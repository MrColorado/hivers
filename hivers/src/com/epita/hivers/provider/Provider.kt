package com.epita.hivers.provider

import java.lang.reflect.Method
import java.util.*

interface Provider<BEAN_TYPE> {
    fun provide() : BEAN_TYPE?
    fun providesForClass() : Class<BEAN_TYPE>

    fun before(method: Method?, lambda: () -> Unit)
    fun after(method: Method?, lambda: () -> Unit)
    fun around(method: Method?, lambda: () -> Unit)
}