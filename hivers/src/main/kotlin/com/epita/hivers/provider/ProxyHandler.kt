package com.epita.hivers.provider

import com.epita.hivers.annotations.Pure
import com.epita.hivers.exception.MethodOrProxyNullException
import com.epita.hivers.interfaces.Aspect
import com.epita.hivers.provider.aspects.After
import com.epita.hivers.provider.aspects.Before
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ProxyHandler<BEAN_TYPE>(private var bean: BEAN_TYPE, private var aspectList: MutableList<Aspect<BEAN_TYPE>>) :
    InvocationHandler {

    @Pure
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (null == method || null == proxy)
            throw MethodOrProxyNullException()
        aspectList.stream().filter { aspect -> aspect is Before }
            .forEach { aspect -> aspect.execute(bean, method, args) }
        val result = if (args == null) {
            method.invoke(bean)
        } else {
            method.invoke(bean, *args)
        }
        aspectList.stream().filter { aspect -> aspect is After }
            .forEach { aspect -> aspect.execute(bean, method, args) }
        return result
    }
}