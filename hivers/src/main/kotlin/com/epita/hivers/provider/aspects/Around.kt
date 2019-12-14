package com.epita.hivers.provider.aspects

import com.epita.hivers.annotations.Pure
import com.epita.hivers.core.InvocationContext
import com.epita.hivers.interfaces.Aspect
import java.lang.reflect.Method

class Around<BEAN_TYPE>(private var lambda: (InvocationContext<BEAN_TYPE>) -> Any?, private var methodName: String) :
    Aspect<BEAN_TYPE> {

    @Pure
    override fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?): Any? {
        val context = InvocationContext(method, args, bean)
        if (methodName == method.name)
            return lambda(context)
        if (null == args)
            return method.invoke(bean)
        return method.invoke(bean, args)
    }
}