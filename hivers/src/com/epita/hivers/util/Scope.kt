package com.epita.hivers.util

import com.epita.hivers.provider.Provider

class Scope {
    private val providers : List<Provider<*>>

    constructor(providers: List<Provider<*>>) {
        this.providers = providers
    }

    /*
    fun getProvidersForClass(classType: Class<*>) : Provider<*> {
        return providers.stream().map{ provider -> provider.providesForClass() as Provider<classType>}
    }
    */
}