package com.epita.hivers.util

import com.epita.hivers.annotations.NotNull
import com.epita.hivers.annotations.NotPure
import com.epita.hivers.annotations.Pure
import com.epita.hivers.provider.Provider
import java.lang.Exception
import java.lang.reflect.Method
import java.lang.reflect.Proxy
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

    @NotPure
    fun bean(@NotNull any: Any) {
        val topScope = stack.peekFirst()
        topScope.bean(any)
    }

    @NotPure
    fun <BEAN_TYPE> bean(@NotNull classType: Class<BEAN_TYPE>, @NotNull any: BEAN_TYPE) {
        val topScope = stack.peekFirst()
        topScope.bean(classType, any)
    }

    @NotPure
    fun <BEAN_TYPE> bean(@NotNull classType: Class<BEAN_TYPE>, @NotNull obj: BEAN_TYPE,
                         @NotNull lambda: Provider<BEAN_TYPE>.() -> Unit) {
        val topScope = stack.peekFirst()
        topScope.bean(classType, obj, lambda)
        /*
        val adapter = BeforeAdapter()
        Proxy.newProxyInstance(
            classType.classLoader,
            arrayOf(classType),
            adapter
        )
        */
    }

    @Pure
    @NotNull
    fun <BEAN_TYPE> instanceOf(@NotNull expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = getProviderClass(expectedClass)
        return provider.provide() ?: throw Exception("Implementation not found")
    }

    @NotPure
    fun scope(@NotNull lambda: Scope.() -> Unit) {
        val scope = Scope()
        lambda.invoke(scope)
        stack.push(scope)
    }

    @NotPure
    fun <BEAN_TYPE> provider(@NotNull expectedClass: Class<BEAN_TYPE>, @NotNull provider: Provider<BEAN_TYPE>) {
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