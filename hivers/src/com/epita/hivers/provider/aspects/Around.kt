package com.epita.hivers.provider.aspects

import com.epita.hivers.core.InvocationContext
import com.epita.hivers.interfaces.Aspect
import java.lang.reflect.Method

class Around<BEAN_TYPE> : Aspect<BEAN_TYPE> {

    private var lambda: (InvocationContext<BEAN_TYPE>) -> Any?
    private var methodName: String
    constructor(lambda: (InvocationContext<BEAN_TYPE>) -> Any?, methodName: String) {
        this.lambda = lambda
        this.methodName = methodName
    }

    override fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?) : Any? {
        val context = InvocationContext(method, args, bean)
        if (methodName == method.name)
            return lambda(context)
        if (null == args)
            return method.invoke(bean)
        return method.invoke(bean, args)
    }
}