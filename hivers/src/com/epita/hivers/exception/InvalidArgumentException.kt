package com.epita.hivers.exception
import kotlin.RuntimeException

class InvalidArgumentException(private val field: String, private val fault: Fault) : RuntimeException() {
    override val message: String?
        get() = this.field + " " + fault.message
}