package com.epita.brokerclient.client

import io.javalin.http.Context

interface ClientControllerInterface {
    val getMessage: (Context) -> Unit
}