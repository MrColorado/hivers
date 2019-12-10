package com.epita.hivers.util

import java.lang.reflect.Method

interface Aspect<BEAN_TYPE> {

    fun execute(bean: BEAN_TYPE, method: Method, args: Array<out Any>?)
}