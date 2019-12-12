package com.epita.busevent

import com.epita.busevent.broker.BusServiceInterface
import com.epita.busevent.broker.BusController
import com.epita.busevent.broker.BusService
import com.epita.busevent.broker.BusControllerInterface
import com.epita.hivers.core.Hivers
import io.javalin.Javalin

fun main() {
    val app = Javalin.create()


    val hivers = Hivers {
        bean(BusServiceInterface::class.java, BusService())
        bean(BusControllerInterface::class.java, BusController(instanceOf(BusServiceInterface::class.java)))
    }

    val busController = hivers.instanceOf(BusControllerInterface::class.java)

    app.start(7000)
        .get("") { context -> context.html("Hello!") }
        .post("/topics", busController.createTopic)
        .delete("/topics", busController.deleteTopic)
        .get("/clients", busController.listClients)
        .post("/subscribe", busController.subscribe)
        .post("/unsubscribe", busController.unsubscribe)
        .post("/publish", busController.publish)

}