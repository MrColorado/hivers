package com.epita.hivers.provider

import com.epita.hivers.annotations.NotNull
import com.epita.hivers.annotations.Pure
import java.util.function.Supplier

class Prototype<BEAN_TYPE> : Provider<BEAN_TYPE> {
    private val providesClass: Class<BEAN_TYPE>
    private val supplier: Supplier<BEAN_TYPE>

    constructor(providesClass: Class<BEAN_TYPE>,
                supplier: Supplier<BEAN_TYPE>) {
        this.providesClass = providesClass
        this.supplier = supplier
    }

    constructor(providesClass: Class<BEAN_TYPE>,
                supplier: Supplier<BEAN_TYPE>,
                initializer: Prototype<BEAN_TYPE>.() -> Unit) {
        this.providesClass = providesClass
        this.supplier = supplier
        initializer.invoke(this)
    }

    @Pure
    @NotNull
    override fun provide(): BEAN_TYPE? {
        return supplier.get()
    }

    @Pure
    @NotNull
    override fun providesForClass(): Class<BEAN_TYPE> {
        return providesClass
    }

}