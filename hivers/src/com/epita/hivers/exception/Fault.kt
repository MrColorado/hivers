package com.epita.hivers.exception

enum class Fault(val message: String, val validationPredicate: (Any?) -> Boolean) {
    NULL("is null", { o -> o == null });

    fun <BEAN_TYPE> validate(obj: BEAN_TYPE?, field: String) : BEAN_TYPE {
        if (validationPredicate(obj))
            throw forField(field)
        return obj!!
    }

    private fun forField(field: String) : InvalidArgumentException {
        return InvalidArgumentException(field, this)
    }
}