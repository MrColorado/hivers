package com.epita.busevent.broker

import io.javalin.http.Context
import com.epita.busevent.models.Topic
import io.javalin.http.Handler

class BusController(private val service: BusServiceInterface) : BusControllerInterface {
    override val createTopic: (Context) -> Unit = {
        val obj = it.bodyAsClass(Topic::class.java)
        val result = service.createTopic(obj.name)
        it.json(result)
    }

    override val helloBus: (Context) -> Unit = {
        it.html("Hello from Bus")
    }

    override val deleteTopic: (Context) -> Unit = {
        it.html("delete topic")
    }

    override val listClients: (Context) -> Unit = {
        val clients = service.listClients()
        it.json(clients)
    }

    override val subscribe: (Context) -> Unit = {
        it.html("subscribe")
    }

    override val unsubscribe: (Context) -> Unit = {
        it.html("unsubscribe")
    }

    override val publish: (Context) -> Unit = {
        it.html("publish")
    }
}