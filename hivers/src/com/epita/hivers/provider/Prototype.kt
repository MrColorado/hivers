package com.epita.hivers.provider

import java.util.function.Supplier

class Prototype<BEAN_TYPE> : Provider<BEAN_TYPE> {
    constructor(supplier: Supplier<BEAN_TYPE>) {

    }

    override fun provide(): BEAN_TYPE {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun providesForClass(): Class<BEAN_TYPE> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}