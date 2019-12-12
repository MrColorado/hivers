package com.epita

import com.epita.brokerclient.ClientService
import org.junit.Test

class BasicClientTest {

    @Test
    fun `test basic index use case`() {
        try {
            val clientService = ClientService("http://localhost:7000/")
            var value = clientService.createTopic("toto")
            println(value)
        }
        catch(e: Exception) {
            return
        }
    }
}
