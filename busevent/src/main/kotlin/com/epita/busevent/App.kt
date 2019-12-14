package com.epita.busevent

import com.epita.busevent.broker.BusServiceInterface
import com.epita.busevent.broker.BusController
import com.epita.busevent.broker.BusService
import com.epita.busevent.broker.BusControllerInterface
import com.epita.hivers.core.Hivers
import com.epita.models.Constants
import io.javalin.Javalin

fun main() {
    val app = Javalin.create { config ->
        config.requestCacheSize = Constants.maxBodySize
    }

    val hivers = Hivers {
        bean(BusServiceInterface::class.java, BusService())
        bean(BusControllerInterface::class.java, BusController(instanceOf(BusServiceInterface::class.java)))
    }

    val busController = hivers.instanceOf(BusControllerInterface::class.java)

    app.start(Constants.serverPort)
        .get("") { context -> context.html("Hello!") }
        .get("/clients", busController.listClients)
        .post("/subscribe", busController.subscribe)
        .post("/unsubscribe", busController.unsubscribe)
        .post("/publish", busController.publish)
}