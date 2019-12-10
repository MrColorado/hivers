package com.epita.hivers.util

import com.epita.hivers.annotations.NotNull
import com.epita.hivers.annotations.NotPure
import com.epita.hivers.annotations.Pure
import com.epita.hivers.provider.Provider
import com.epita.hivers.provider.Singleton
import java.util.*
import java.util.function.Supplier

class Scope {
    private val providers : MutableList<Provider<*>>

    constructor() {
        this.providers = ArrayList()
    }

    constructor(@NotNull initializer: Scope.() -> Unit) {
        this.providers = ArrayList()
        initializer.invoke(this)
    }

    @NotPure
    private fun createOrReplace(@NotNull classType: Class<*>, @NotNull provider: Provider<*>) {
        val index = providers.indexOfFirst { p -> p.javaClass == classType }
        if (index < 0) {
            providers.add(provider)
        } else {
            providers[index] = provider
        }
    }

    @NotPure
    fun bean(@NotNull any: Any) {
        bean(any.javaClass, any)
    }

    @NotPure
    fun <BEAN_TYPE> bean(@NotNull classType: Class<BEAN_TYPE>, @NotNull any: BEAN_TYPE) {
        val singleton = Singleton(classType, Supplier { any })
        provider(classType, singleton)
    }

    @NotPure
    fun <BEAN_TYPE> bean(@NotNull classType: Class<BEAN_TYPE>, @NotNull any: BEAN_TYPE,
                         @NotNull lambda: Provider<BEAN_TYPE>.() -> Unit) {
        val singleton = Singleton(classType, Supplier { any }, lambda)
        provider(classType, singleton)
    }

    @Pure
    @NotNull
    fun <BEAN_TYPE> getProviderForClass(@NotNull expectedClass: Class<BEAN_TYPE>) : Optional<Provider<BEAN_TYPE>> {
        return providers.stream()
                .filter { provider -> provider.providesForClass().isAssignableFrom(expectedClass) }
                .map { provider -> provider as Provider<BEAN_TYPE>}
                .findFirst()
    }

    @NotPure
    fun <BEAN_TYPE> provider(@NotNull expectedClass : Class<BEAN_TYPE>, @NotNull provider: Provider<BEAN_TYPE>) {
        createOrReplace(expectedClass, provider)
    }

}