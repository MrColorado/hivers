package com.epita.hivers.exception

class AspectNullMethodException: RuntimeException() {
    override val message: String?
        get() = "Cannot aspect null method."
}