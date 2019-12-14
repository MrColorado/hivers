package com.epita.hivers.core

import com.epita.hivers.annotations.NotPure
import com.epita.hivers.annotations.Pure
import com.epita.hivers.exception.ImplementationNotFoundException
import com.epita.hivers.interfaces.Provider
import com.epita.hivers.interfaces.ScopeStack
import java.lang.Exception
import java.util.*

class Hivers(initializer: Hivers.() -> Unit) : Scope(), ScopeStack {

    private val stack: Deque<Scope> = ArrayDeque()

    init {
        stack.push(Scope())
        initializer.invoke(this)
    }

    override fun getScopeStack(): Deque<Scope> {
        return stack
    }

    @NotPure
    override fun bean(any: Any) {
        stack.peekFirst().bean(any)
    }

    @NotPure
    override fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, any: BEAN_TYPE) {
        stack.peekFirst().bean(classType, any)
    }

    @NotPure
    override fun <BEAN_TYPE> bean(classType: Class<BEAN_TYPE>, obj: BEAN_TYPE,
                                  lambda: Provider<BEAN_TYPE>.() -> Unit) {
        stack.peekFirst().bean(classType, obj, lambda)
    }

    @Pure
    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = getProviderClass(expectedClass)
        return provider.provide() ?: throw ImplementationNotFoundException()
    }

    @NotPure
    fun scope(lambda: Scope.() -> Unit) {
        val scope = Scope(lambda)
        stack.push(scope)
    }

    @NotPure
    override fun <BEAN_TYPE> provider(expectedClass: Class<BEAN_TYPE>, provider: Provider<BEAN_TYPE>) {
        stack.peekFirst().provider(expectedClass, provider)
    }
}