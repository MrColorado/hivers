package com.epita.busevent.broker

import io.javalin.http.Context

interface BusControllerInterface {
    val createTopic: (Context) -> Unit
    val helloBus: (Context) -> Unit
    val deleteTopic: (Context) -> Unit
    val listClients: (Context) -> Unit
    val subscribe: (Context) -> Unit
    val unsubscribe: (Context) -> Unit
    val publish: (Context) -> Unit
}