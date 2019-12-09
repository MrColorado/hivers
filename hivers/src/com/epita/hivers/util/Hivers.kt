package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import java.lang.Exception
import java.lang.reflect.Method
import kotlin.collections.ArrayList

class Hivers {
    private val providerList: List<Provider<*>> = ArrayList()
    private val scopeStack: List<Provider<*>> = ArrayList()

    constructor(initializer: Hivers.() -> Unit) {
        initializer.invoke(this)
    }

    fun bean(any: Any) {

    }

    fun bean(classType: Class<out Any>, any: Any) {

    }

    fun bean(classType: Class<out Any>, any: Any, lambda: () -> Unit) {

    }

    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val instance = providerList.stream()
                .filter { provider -> provider.providesForClass().isAssignableFrom(expectedClass) }
                .map { provider -> provider as Provider<BEAN_TYPE>}
                .findFirst()
                .map {provider -> provider.provide()}
        if (instance.isEmpty)
            throw Exception("class not found")
        return instance.get()
    }
    fun <BEAN_TYPE> provider(expectedClass : Class<BEAN_TYPE>, prototype: Provider<BEAN_TYPE>) {

    }

    fun scope(lambda: () -> Unit) {

    }

    fun before(method: Method?, lambda: () -> Unit) {

    }

    /*
    fun around(method: Method?, lambda: (ctx: Contex) -> Unit) {

    }
    */

    fun after(method: Method?, lambda: () -> Unit) {

    }
}