package com.epita.brokerclient.client

import com.epita.brokerclient.LoggerInterface
import io.javalin.http.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ClientController : ClientControllerInterface, LoggerInterface {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override val getMessage: (Context) -> Unit = {
        val body = it.body()
    }

    override fun getLogger(): Logger {
        return logger
    }
}