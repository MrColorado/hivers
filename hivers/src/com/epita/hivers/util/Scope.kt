package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import com.epita.hivers.provider.Singleton
import java.util.*
import java.util.function.Supplier

class Scope {
    private val providers : MutableList<Provider<*>>

    constructor(providers: MutableList<Provider<*>>) {
        this.providers = providers
    }

    constructor() {
        this.providers = ArrayList()
    }

    fun bean(any: Any) {
        val singleton = Singleton(any.javaClass, Supplier { any })
        providers.add(0, singleton)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE) {
        val singleton = Singleton(classType, Supplier { any })
        providers.add(0, singleton)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE, lambda: () -> Unit) {
        val singleton = Singleton(classType, Supplier{ any })
        providers.add(0, singleton)
        lambda()
    }

    fun <BEAN_TYPE> getProviderForClass(expectedClass: Class<BEAN_TYPE>) : Optional<Provider<BEAN_TYPE>> {
        return providers.stream()
                .filter { provider -> provider.providesForClass().isAssignableFrom(expectedClass) }
                .map { provider -> provider as Provider<BEAN_TYPE>}
                .findFirst()
    }

}