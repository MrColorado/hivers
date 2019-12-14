package com.epita.hivers.provider.aspects

import com.epita.hivers.annotations.Pure
import com.epita.hivers.interfaces.Aspect
import java.lang.reflect.Method

abstract class AbstractAspect<BEAN_TYPE>(private val lambda: () -> Unit, private val methodName: String) :
    Aspect<BEAN_TYPE> {

    @Pure
    override fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?): Any? {
        if (methodName == method.name)
            return lambda()
        return null
    }
}