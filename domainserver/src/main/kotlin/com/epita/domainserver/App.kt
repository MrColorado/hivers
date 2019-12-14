package com.epita.domainserver

import com.epita.brokerclient.client.BrokerClient
import com.epita.domainserver.scraper.Scraper
import com.epita.hivers.core.Hivers
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
    }

    val publisher =
        Publisher(hivers.instanceOf(BrokerClientInterface::class.java))
    val scraper = Scraper.ScraperOrchestrator(hivers.instanceOf(BrokerClientInterface::class.java), publisher)

    scraper.start()
}