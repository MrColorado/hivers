package com.epita.hivers.interfaces

import java.lang.reflect.Method

interface Provider<BEAN_TYPE> {
    fun provide() : BEAN_TYPE?
    fun providesForClass() : Class<BEAN_TYPE>

    fun before(method: Method?, lambda: () -> Unit)
    fun after(method: Method?, lambda: () -> Unit)
    fun around(method: Method?, lambda: () -> Unit)
}