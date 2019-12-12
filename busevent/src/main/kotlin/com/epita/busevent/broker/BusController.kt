package com.epita.busevent.broker

import io.javalin.http.Context
import com.epita.busevent.models.Topic

class BusController {

    private val service: BusService = BusService()

    val createTopic: (Context) -> Unit = {
        val obj = it.bodyAsClass(Topic::class.java)
        val result = service.createTopic(obj.name)
        it.json(result)
    }
}