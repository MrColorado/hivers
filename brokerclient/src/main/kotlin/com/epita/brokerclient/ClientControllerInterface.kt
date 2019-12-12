package com.epita.brokerclient

import io.javalin.http.Context

interface ClientControllerInterface {
    val getMessage: (Context) -> Unit
}