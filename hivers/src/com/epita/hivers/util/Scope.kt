package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import com.epita.hivers.provider.Singleton
import java.util.*
import java.util.function.Supplier

class Scope {
    private val providers : MutableList<Provider<*>>

    constructor() {
        this.providers = ArrayList()
    }

    constructor(initializer: Scope.() -> Unit) {
        this.providers = ArrayList()
        initializer.invoke(this)
    }

    private fun createOrReplace(classType: Class<*>, provider: Provider<*>) {
        val index = providers.indexOfFirst { p -> p.javaClass == classType }
        if (index < 0) {
            providers.add(provider)
        } else {
            providers[index] = provider
        }
    }

    fun bean(any: Any) {
        bean(any.javaClass, any)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE) {
        val singleton = Singleton(classType, Supplier { any })
        provider(classType, singleton)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE, lambda: Provider<BEAN_TYPE>.() -> Unit) {
        val singleton = Singleton(classType, Supplier { any }, lambda)
        provider(classType, singleton)
    }

    fun <BEAN_TYPE> getProviderForClass(expectedClass: Class<BEAN_TYPE>) : Optional<Provider<BEAN_TYPE>> {
        return providers.stream()
                .filter { provider -> provider.providesForClass().isAssignableFrom(expectedClass) }
                .map { provider -> provider as Provider<BEAN_TYPE>}
                .findFirst()
    }

    fun <BEAN_TYPE> provider(expectedClass : Class<BEAN_TYPE>, provider: Provider<BEAN_TYPE>) {
        createOrReplace(expectedClass, provider)
    }

}