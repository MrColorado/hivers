package com.epita.hivers.util

import com.epita.hivers.provider.Provider
import java.lang.Exception
import java.util.*

interface ScopeStack {

    fun getScopeStack() : Deque<Scope>

    fun peek() : Scope {
        return getScopeStack().peek()
    }

    fun push(scope: Scope) {
        getScopeStack().push(scope)
    }

    fun pop() : Scope {
        return getScopeStack().pop()
    }


    fun <BEAN_TYPE> getProviderClass(classType: Class<BEAN_TYPE>) : Provider<BEAN_TYPE> {
        val stack = getScopeStack()
        for (scope: Scope in stack) {
            val provider = scope.getProviderForClass(classType)
            if (provider.isPresent)
                return provider.get()
        }
        throw Exception("class not found")
    }
}