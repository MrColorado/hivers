package com.epita.hivers.provider.aspects

import com.epita.hivers.interfaces.Aspect
import java.lang.reflect.Method

abstract class AbstractAspect<BEAN_TYPE> : Aspect<BEAN_TYPE> {
    private val lambda : () -> Unit
    private val methodName : String

    constructor(lambda: () -> Unit, methodName: String) {
        this.lambda = lambda
        this.methodName = methodName
    }

    override fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?) : Any?{
        if (methodName == method.name)
            return lambda()
        return null
    }
}