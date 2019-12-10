package com.epita.hivers.provider

import com.epita.hivers.annotations.Pure

abstract class AbstractProvider<BEAN_TYPE> : Provider<BEAN_TYPE> {

    private val providesClass: Class<BEAN_TYPE>

    constructor(providesClass: Class<BEAN_TYPE>) {
        this.providesClass = providesClass
    }

    @Pure
    override fun providesForClass(): Class<BEAN_TYPE> {
        return providesClass
    }
}