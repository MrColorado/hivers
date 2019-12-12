package com.epita.busevent.broker

import io.javalin.http.Context
import com.epita.busevent.models.Topic

class BusController(private val service: BusServiceInterface) : BusControllerInterface {
    override val createTopic: (Context) -> Unit = {
        val obj = it.bodyAsClass(Topic::class.java)
        val result = service.createTopic(obj.name)
        it.json(result)
    }

    override val helloBus: (Context) -> Unit = {
        it.html("Hello from Bus")
    }
}