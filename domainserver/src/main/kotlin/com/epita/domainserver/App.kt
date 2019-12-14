package com.epita.domainserver

import com.epita.brokerclient.client.BrokerClient
import com.epita.domainserver.scraper.ScraperOrchestrator
import com.epita.domainserver.tfidf.IndexerOrchestrator
import com.epita.hivers.core.Hivers
import com.epita.models.Constants
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient(Constants.serverUrl))
    }

    val publisher =
        Publisher(hivers.instanceOf(BrokerClientInterface::class.java))
    val scraper = ScraperOrchestrator(hivers.instanceOf(BrokerClientInterface::class.java), publisher)
    val index = IndexerOrchestrator(hivers.instanceOf(BrokerClientInterface::class.java), publisher)
    scraper.start()
    index.start()
}