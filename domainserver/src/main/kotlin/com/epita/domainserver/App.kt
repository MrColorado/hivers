package com.epita.domainserver

import com.epita.brokerclient.client.BrokerClient
import com.epita.domainserver.scraper.core.CrawlerOrchestratorServiceInterface
import com.epita.domainserver.scraper.impl.CrawlerOrchestratorService
import com.epita.domainserver.tfidf.core.IndexerOrchestratorServiceInterface
import com.epita.domainserver.tfidf.impl.IndexerOrchestratorService
import com.epita.hivers.core.Hivers
import com.epita.models.Constants
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher
import com.epita.models.communications.PublisherInterface

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient(Constants.serverUrl))
        bean(PublisherInterface::class.java, Publisher(instanceOf(BrokerClientInterface::class.java)))
        bean(CrawlerOrchestratorServiceInterface::class.java,
            CrawlerOrchestratorService(
                instanceOf(BrokerClientInterface::class.java),
                instanceOf(PublisherInterface::class.java)
            ))
        bean(IndexerOrchestratorServiceInterface::class.java,
            IndexerOrchestratorService(
                instanceOf(BrokerClientInterface::class.java),
                instanceOf(PublisherInterface::class.java)
            ))
    }

    val crawlerOrchestrator = hivers.instanceOf(CrawlerOrchestratorServiceInterface::class.java)
    val indexOrchestrator = hivers.instanceOf(IndexerOrchestratorServiceInterface::class.java)
    crawlerOrchestrator.start()
    indexOrchestrator.start()
}