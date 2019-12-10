package com.epita.hivers.provider

import com.epita.hivers.annotations.Pure
import java.util.function.Supplier

class Prototype<BEAN_TYPE> : AbstractProvider<BEAN_TYPE> {
    private val supplier: Supplier<BEAN_TYPE>

    constructor(providesClass: Class<BEAN_TYPE>,
                supplier: Supplier<BEAN_TYPE>) : super(providesClass){
        this.supplier = supplier
    }

    constructor(providesClass: Class<BEAN_TYPE>,
                supplier: Supplier<BEAN_TYPE>,
                initializer: Prototype<BEAN_TYPE>.() -> Unit) : super(providesClass){
        this.supplier = supplier
        initializer.invoke(this)
    }

    @Pure
    override fun provide(): BEAN_TYPE? {
        return supplier.get()
    }
}