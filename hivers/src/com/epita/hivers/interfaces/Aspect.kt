package com.epita.hivers.interfaces

import java.lang.reflect.Method

interface Aspect<BEAN_TYPE> {

    fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?) : Any?
}