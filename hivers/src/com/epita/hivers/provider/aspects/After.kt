package com.epita.hivers.provider.aspects

class After<BEAN_TYPE> : AbstractAspect<BEAN_TYPE> {

    constructor(lambda: () -> Unit, methodName: String) : super(lambda, methodName)
}