package com.epita.busevent.broker

import com.epita.busevent.LoggerInterface
import com.epita.busevent.models.Subscribe
import io.javalin.http.Context
import com.epita.busevent.models.UnSubscribe
import com.epita.models.communications.MessageString
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BusController(private val service: BusServiceInterface) : BusControllerInterface, LoggerInterface {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

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
        val obj = it.bodyAsClass(MessageString::class.java)
        service.publish(obj, it.body())
        it.status(200)
    }

    override fun getLogger(): Logger {
        return logger
    }
}