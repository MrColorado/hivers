package com.epita.busevent.broker

import io.javalin.http.Context

interface BusControllerInterface {
    val listClients: (Context) -> Unit
    val subscribe: (Context) -> Unit
    val unsubscribe: (Context) -> Unit
    val publish: (Context) -> Unit
}