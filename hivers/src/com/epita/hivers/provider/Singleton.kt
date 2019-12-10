package com.epita.hivers.provider

import com.epita.hivers.annotations.NotNull
import com.epita.hivers.annotations.Pure
import java.util.function.Supplier

class Singleton<BEAN_TYPE> : Provider<BEAN_TYPE> {

    private val providesClass: Class<BEAN_TYPE>
    private val supplier: Supplier<BEAN_TYPE>
    private var value: BEAN_TYPE? = null

    constructor(providesClass: Class<BEAN_TYPE>,
                  supplier: Supplier<BEAN_TYPE>) {
        this.providesClass = providesClass
        this.supplier = supplier
    }

    constructor(providesClass: Class<BEAN_TYPE>,
                supplier: Supplier<BEAN_TYPE>,
                initializer: Provider<BEAN_TYPE>.() -> Unit){
        this.providesClass = providesClass
        this.supplier = supplier
        initializer.invoke(this)
    }

    @Pure
    @NotNull
    override fun provide(): BEAN_TYPE? {
        if (null == value) {
            value = supplier.get()
        }
        return value
    }

    @Pure
    @NotNull
    override fun providesForClass(): Class<BEAN_TYPE> {
        return providesClass
    }

}