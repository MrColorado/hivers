package com.epita.busevent.broker

import com.epita.busevent.LoggerInterface
import com.epita.busevent.models.Message
import com.epita.busevent.models.Subscribe
import io.javalin.http.Context
import com.epita.busevent.models.Topic
import com.epita.busevent.models.UnSubscribe
import io.javalin.http.Handler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.log

class BusController(private val service: BusServiceInterface) : BusControllerInterface, LoggerInterface {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override val createTopic: (Context) -> Unit = {
        val obj = it.bodyAsClass(Topic::class.java)
        val result = service.createTopic(obj.name)
        it.json(result)
    }

    override val helloBus: (Context) -> Unit = {
        it.html("Hello from Bus")
    }

    override val deleteTopic: (Context) -> Unit = {
        val obj = it.bodyAsClass(Topic::class.java)
        val result = service.deleteTopic(obj.name)
        it.json(result)
    }

    override val listClients: (Context) -> Unit = {
        val clients = service.listClients()
        it.json(clients)
    }

    override val subscribe: (Context) -> Unit = {
        val obj = it.bodyAsClass(Subscribe::class.java)
        val id = service.subscribe(obj.url, obj.topic)
        if (id == null) {
            it.status(404)
        }
        else {
            it.json(id)
        }
    }

    override val unsubscribe: (Context) -> Unit = {
        val obj = it.bodyAsClass(UnSubscribe::class.java)
        val result = service.unsubscribe(obj.url, obj.topic)
        it.json(result)
    }

    override val publish: (Context) -> Unit = {
        val obj = it.bodyAsClass(Message::class.java)
        service.publish(obj)
        it.status(200)
    }

    override fun getLogger(): Logger {
        return logger
    }
}