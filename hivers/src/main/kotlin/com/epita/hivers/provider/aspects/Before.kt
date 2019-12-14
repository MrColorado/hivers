package com.epita.hivers.provider.aspects

class Before<BEAN_TYPE>(lambda: () -> Unit, methodName: String) : AbstractAspect<BEAN_TYPE>(lambda, methodName)