package com.epita.hivers.test

class TestServiceBlipImpl : TestService {
    override fun pong(): String {
        println("Execution ")
        return "blip"
    }

    override fun ping() = println("ping")

}