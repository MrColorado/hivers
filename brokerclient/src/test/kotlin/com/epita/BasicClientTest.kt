package com.epita

import com.epita.brokerclient.client.ClientService
import com.epita.brokerclient.models.MessageType
import org.junit.Test

class BasicClientTest {

    @Test
    fun `test basic index use case`() {
        try {
            val clientService = ClientService("http://localhost:7000/")
            var value = clientService.createTopic("toto")
            println(value)

            val id = clientService.subscribe("toto")
            println(id)

            val clients = clientService.listClients()
            println(clients)

            value = clientService.publish("toto", "kek", MessageType.BROADCAST)
            println(value)

            value = clientService.deleteTopic("toto")
            println(value)
        }
        catch(e: Exception) {
            return
        }
    }
}
