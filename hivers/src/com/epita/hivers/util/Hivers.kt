package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import com.epita.hivers.provider.Singleton
import java.lang.Exception
import java.lang.reflect.Method
import java.util.*
import java.util.function.Supplier
import kotlin.collections.ArrayList

class Hivers : ScopeStack {

    private val providerList: MutableList<Provider<*>> = ArrayList()
    private val stack: Deque<Scope> = ArrayDeque()

    constructor(initializer: Hivers.() -> Unit) {
        initializer.invoke(this)
    }

    override fun getScopeStack(): Deque<Scope> {
        return stack;
    }

    fun bean(any: Any) {
        val singleton = Singleton(any.javaClass, Supplier { any })
        providerList.add(0, singleton)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE) {
        val singleton = Singleton(classType, Supplier { any })
        providerList.add(0, singleton)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE, lambda: () -> Unit) {
        val singleton = Singleton(classType, Supplier{ any })
        providerList.add(0, singleton)
        lambda()
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

    fun scope(lambda: () -> Unit) {
        stack.push(Scope(providerList))
        lambda()
        stack.pop()
    }

    fun <BEAN_TYPE> provider(expectedClass : Class<BEAN_TYPE>, prototype: Provider<BEAN_TYPE>) {

    }

    fun before(method: Method?, lambda: () -> Unit) {

    }

    fun around(method: Method?, lambda: (ctx: Method) -> Unit) {

    }

    fun after(method: Method?, lambda: () -> Unit) {

    }
}