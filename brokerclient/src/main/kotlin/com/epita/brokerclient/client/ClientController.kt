package com.epita.brokerclient.client

import io.javalin.http.Context
import org.slf4j.LoggerFactory

class ClientController : ClientControllerInterface {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override val getMessage: (Context) -> Unit = {
        val body = it.body()
    }
}