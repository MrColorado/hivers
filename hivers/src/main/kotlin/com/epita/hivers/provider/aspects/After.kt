package com.epita.hivers.provider.aspects

class After<BEAN_TYPE>(lambda: () -> Unit, methodName: String) : AbstractAspect<BEAN_TYPE>(lambda, methodName)