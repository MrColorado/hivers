package com.epita.hivers.exception

class MethodOrProxyNullException : RuntimeException() {
    override val message: String?
        get() = "Method or proxy is null."
}