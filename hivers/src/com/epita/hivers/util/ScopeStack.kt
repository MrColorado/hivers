package com.epita.hivers.util

import com.epita.hivers.annotations.NotPure
import com.epita.hivers.annotations.Pure
import com.epita.hivers.provider.Provider
import java.lang.Exception
import java.util.*

interface ScopeStack {

    fun getScopeStack(): Deque<Scope>
    val MIN_STACK_SIZE: Int
        get() = 1


    @Pure
    fun peek(): Scope {
        return getScopeStack().peek()
    }

    @NotPure
    fun push(scope: Scope) {
        getScopeStack().push(scope)
    }

    @NotPure
    fun pop() {
        if (MIN_STACK_SIZE == getScopeStack().size) {
            throw Exception("There is no scope left")
        }
        getScopeStack().pop()
    }


    @Pure
    fun <BEAN_TYPE> getProviderClass(classType: Class<BEAN_TYPE>): Provider<BEAN_TYPE> {
        val stack = getScopeStack()
        for (scope: Scope in stack) {
            val provider = scope.getProviderForClass(classType)
            if (provider.isPresent)
                return provider.get()
        }
        throw Exception("class not found")
    }
}