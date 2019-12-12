package com.epita.brokerclient

import io.javalin.http.Context

class ClientController : ClientControllerInterface {

    override val getMessage: (Context) -> Unit = {
        val body = it.body()
    }
}