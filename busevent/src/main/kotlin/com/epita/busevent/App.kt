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

    app.start(7000)
    app.get("") { context -> context.html("Hello!") }
    app.get("/rest", hivers.instanceOf(BusControllerInterface::class.java).helloBus)
}