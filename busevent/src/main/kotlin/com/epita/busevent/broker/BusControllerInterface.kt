package com.epita.busevent.broker

import io.javalin.http.Context

interface BusControllerInterface {
    val createTopic: (Context) -> Unit
    val helloBus: (Context) -> Unit
}