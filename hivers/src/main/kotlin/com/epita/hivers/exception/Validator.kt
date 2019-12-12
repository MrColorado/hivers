package com.epita.hivers.exception

class Validator {
    companion object {
        fun check(): Validator = Validator()
    }

    fun <BEAN_TYPE> notNull(obj: BEAN_TYPE?, field: String) : Validator {
        Fault.NULL.validate(obj, field)
        return this
    }
}