package com.epita.hivers.core

import java.lang.reflect.Method

class InvocationContext<BEAN_TYPE> {
    var method: Method
    private var args: Array<out Any>?
    private var bean: BEAN_TYPE

    constructor(method: Method, args: Array<out Any>?, bean: BEAN_TYPE) {
        this.method = method
        this.args = args
        this.bean = bean
    }

    fun proceed() : Any? {
        if (null == args)
            return method.invoke(bean)
        return method.invoke(bean, args)
    }
}