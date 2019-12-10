package com.epita.hivers.provider.aspects

class Before<BEAN_TYPE> : AbstractAspect<BEAN_TYPE> {
    constructor(lambda: () -> Unit, methodName: String) : super(lambda, methodName)
}