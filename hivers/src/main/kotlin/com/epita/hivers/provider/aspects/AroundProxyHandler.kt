package com.epita.hivers.provider.aspects

import com.epita.hivers.annotations.Pure
import com.epita.hivers.exception.MethodOrProxyNullException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class AroundProxyHandler<BEAN_TYPE>(private var bean: BEAN_TYPE, private var aspect: Around<BEAN_TYPE>) :
    InvocationHandler {

    @Pure
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (null == method || null == proxy)
            throw MethodOrProxyNullException()
        return aspect.execute(bean, method, args)
    }
}