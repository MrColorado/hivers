package com.epita.domain

import com.epita.brokerclient.client.BrokerClient
import com.epita.domain.scraper.Scraper
import com.epita.hivers.core.Hivers
import com.epita.models.BrokerClientInterface
import com.epita.models.Publisher

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
    }

    val publisher = Publisher(hivers.instanceOf(BrokerClientInterface::class.java))
    val scraper = Scraper(hivers.instanceOf(BrokerClientInterface::class.java), publisher)
    scraper.start()
}