package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import java.lang.Exception
import java.lang.reflect.Method
import java.util.*

class Hivers : ScopeStack {

    private val stack: Deque<Scope> = ArrayDeque()

    constructor(initializer: Hivers.() -> Unit) {
        stack.push(Scope())
        initializer.invoke(this)
    }

    override fun getScopeStack(): Deque<Scope> {
        return stack
    }

    fun bean(any: Any) {
        val topScope = stack.peekFirst()
        topScope.bean(any)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE) {
        val topScope = stack.peekFirst()
        topScope.bean(classType, any)
    }

    fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE, lambda: () -> Unit) {
        val topScope = stack.peekFirst()
        topScope.bean(classType, any)
    }

    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = getProviderClass(expectedClass)
        return provider.provide() ?: throw Exception("Implementation not found")
    }

    fun scope(lambda: () -> Unit) {

    }

    fun <BEAN_TYPE> provider(expectedClass : Class<BEAN_TYPE>, provider: Provider<BEAN_TYPE>) {
        val topScope = stack.peekFirst()
        topScope.provider(expectedClass, provider)
    }

    fun before(method: Method?, lambda: () -> Unit) {

    }

    fun around(method: Method?, lambda: (ctx: Method) -> Unit) {

    }

    fun after(method: Method?, lambda: () -> Unit) {

    }
}