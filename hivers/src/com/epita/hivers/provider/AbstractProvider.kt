package com.epita.hivers.provider

import com.epita.hivers.annotations.Pure
import com.epita.hivers.util.Aspect
import com.epita.hivers.util.Hivers
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
        Proxy.newProxyInstance(
            Hivers::class.java.classLoader,
            arrayOf(providesClass),
            adapter
        )
        return bean
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
    override fun around(method: Method?, lambda: () -> Unit) {
        throw RuntimeException("Not implemented")
    }
}