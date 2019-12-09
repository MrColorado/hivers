package com.epita.hivers.util

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class BeforeAdapter : InvocationHandler {
    private val obj: Any
    private val lambda: () -> Unit

    constructor(obj: Any, lambda: () -> Unit) {
        this.obj = obj
        this.lambda = lambda
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if (method == null || proxy == null || args == null)
            throw Exception("Problem with invoke")

        lambda()
        return method.invoke(obj, args)
    }
}