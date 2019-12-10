package com.epita.hivers.interfaces

import com.epita.hivers.annotations.NotPure
import com.epita.hivers.annotations.Pure
import com.epita.hivers.exception.ProviderClassNotFoundException
import com.epita.hivers.exception.ScopeEmptyException
import com.epita.hivers.core.Scope
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
            throw ScopeEmptyException()
        }
        getScopeStack().pop()
    }


    @Pure
    fun <BEAN_TYPE> getProviderClass(classType: Class<BEAN_TYPE>): Provider<BEAN_TYPE> {
        val stack = getScopeStack()

        for (scope: Scope in stack) {
            val provider = scope.getProviderForClass(classType)
            if (provider != null)
                return provider
        }

        throw ProviderClassNotFoundException(classType)
    }
}