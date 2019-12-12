package com.epita.hivers.provider

import com.epita.hivers.annotations.Pure
import com.epita.hivers.interfaces.Provider
import com.epita.hivers.interfaces.Aspect
import com.epita.hivers.core.Hivers
import com.epita.hivers.core.InvocationContext
import com.epita.hivers.provider.aspects.After
import com.epita.hivers.provider.aspects.Around
import com.epita.hivers.provider.aspects.AroundProxyHandler
import com.epita.hivers.provider.aspects.Before
import java.lang.reflect.Method
import java.lang.reflect.Proxy

abstract class AbstractProvider<BEAN_TYPE> : Provider<BEAN_TYPE> {

    private val providesClass: Class<BEAN_TYPE>
    private val aspects: MutableList<Aspect<BEAN_TYPE>>

    constructor(providesClass: Class<BEAN_TYPE>) {
        this.providesClass = providesClass
        this.aspects = ArrayList()
    }

    @Pure
    override fun providesForClass(): Class<BEAN_TYPE> {
        return providesClass
    }

    @Pure
    fun proxify(bean: BEAN_TYPE): BEAN_TYPE {
        val adapter = ProxyHandler(bean, aspects)
        var proxy = Proxy.newProxyInstance(
                this.javaClass.classLoader,
                arrayOf(providesClass),
                adapter
        ) as BEAN_TYPE
        val arounds = aspects.filter { a -> a is Around}.map {a -> a as Around}
        arounds.forEach {a ->
            proxy = Proxy.newProxyInstance(
                    this.javaClass.classLoader,
                    arrayOf(providesClass),
                    AroundProxyHandler(proxy, a)
            ) as BEAN_TYPE
        }
        return proxy
    }

    override fun before(method: Method?, lambda: () -> Unit) {
        if (method == null)
            throw java.lang.RuntimeException("Cannot aspect null method")
        this.aspects.add(Before(lambda, method.name))
    }

    override fun after(method: Method?, lambda: () -> Unit) {
        if (method == null)
            throw java.lang.RuntimeException("Cannot aspect null method")
        this.aspects.add(After(lambda, method.name))
    }

    override fun around(method: Method?, lambda: (InvocationContext<BEAN_TYPE>) -> Any?) {
        if (method == null)
            throw java.lang.RuntimeException("Cannot aspect null method")
        this.aspects.add(Around(lambda, method.name))
    }
}