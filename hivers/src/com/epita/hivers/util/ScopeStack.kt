package com.epita.hivers.util

import com.epita.hivers.provider.Provider
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

    /*
    fun <BEAN_TYPE> getTopProviderClass(classType: Class<BEAN_TYPE>) : Optional<Provider<BEAN_TYPE>> {
        return getScopeStack().stream()
                .map { scope ->  scope.getProvidersForClass() as  }
    }
    */

}