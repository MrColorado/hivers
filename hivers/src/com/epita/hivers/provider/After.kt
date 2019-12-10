package com.epita.hivers.provider

import com.epita.hivers.util.Aspect
import java.lang.reflect.Method

class After<BEAN_TYPE> : AbstractAspect<BEAN_TYPE> {

    constructor(lambda: () -> Unit, methodName: String) : super(lambda, methodName)
}