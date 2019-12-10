package com.epita.hivers.provider

import com.epita.hivers.util.Aspect
import java.lang.reflect.Method

abstract class AbstractAspect<BEAN_TYPE> : Aspect<BEAN_TYPE> {
    private val lambda : () -> Unit
    private val methodName : String

    constructor(lambda: () -> Unit, methodName: String) {
        this.lambda = lambda
        this.methodName = methodName
    }

    override fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?) {
        if (methodName == method.name)
            lambda.invoke()
    }
}