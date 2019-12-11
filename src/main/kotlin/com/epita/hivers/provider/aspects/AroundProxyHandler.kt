package com.epita.hivers.provider.aspects

import com.epita.hivers.annotations.Pure
import com.epita.hivers.interfaces.Aspect
import com.epita.hivers.provider.aspects.After
import com.epita.hivers.provider.aspects.Before
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class AroundProxyHandler<BEAN_TYPE> : InvocationHandler {

    private var bean: BEAN_TYPE
    private var aspect: Around<BEAN_TYPE>

    constructor(bean: BEAN_TYPE, aspect: Around<BEAN_TYPE>) {
        this.bean = bean
        this.aspect = aspect
    }


    @Pure
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (null == method || null == proxy)
            throw RuntimeException("Method or proxy is null")
        return aspect.execute(bean, method, args)
    }
}