package com.epita.hivers.test

class TestServiceImpl : TestService {
    override fun pong(): String = "pong"

    override fun ping() = println("ping")

}