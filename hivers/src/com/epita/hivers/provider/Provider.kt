package com.epita.hivers.provider

import java.util.*

interface Provider<BEAN_TYPE> {
    fun provideOptional() : Optional<BEAN_TYPE> {
        return Optional.ofNullable(provide())
    }
    fun provide() : BEAN_TYPE?
    fun providesForClass() : Class<BEAN_TYPE>
}