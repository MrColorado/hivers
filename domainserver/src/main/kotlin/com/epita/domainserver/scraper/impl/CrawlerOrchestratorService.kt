package com.epita.domainserver.scraper.impl

import com.epita.domainserver.scraper.core.CrawlerOrchestratorServiceInterface
import com.epita.domainserver.scraper.entities.CrawlersStateEntity
import com.epita.domainserver.scraper.subscribers.CrawledEventSubscriber
import com.epita.domainserver.scraper.subscribers.CrawlerInitCommandSubscriber
import com.epita.domainserver.scraper.subscribers.NotCrawledEventSubscriber
import com.epita.models.Constants
import com.epita.models.Topics
import com.epita.models.commands.CrawlerCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.PublisherInterface

class CrawlerOrchestratorService(
    private val brokerClient: BrokerClientInterface, private val publisher: PublisherInterface,
    private val entity: CrawlersStateEntity = CrawlersStateEntity()
) : CrawlerOrchestratorServiceInterface {

    override fun start() {
        entity.toVisitLinks.addAll(Constants.urls)
        CrawlerInitCommandSubscriber(brokerClient, Topics.CRAWLER_INIT_COMMAND.str) { id -> initCrawler(id) }
        CrawledEventSubscriber(brokerClient, Topics.CRAWLED_EVENT.str) { url, urls -> crawledEvent(url, urls) }
        //CrawledSubscriber(brokerClient, Topics.CRAWLED_EVENT.str) { url, urls ->   }
        NotCrawledEventSubscriber(brokerClient, Topics.NOT_CRAWLED_EVENT.str) { url -> notCrawlerEvent(url) }
    }

    private fun startCrawler() {
        var url = entity.toVisitLinks.remove()
        while (entity.visitedLinks.contains(url))
            url = entity.toVisitLinks.remove()
        publisher.publish(Topics.CRAWL_URL_COMMAND.str, CrawlerCommand(url), MessageType.ONCE, CrawlerCommand::class.java)
    }

    private fun initCrawler(id: String) {
        entity.crawlerList.add(id)
        startCrawler()
    }

    private fun crawledEvent(url: String, urls: List<String>) {
        entity.visitedLinks.add(url)
        entity.toVisitLinks.addAll(urls)
        startCrawler()
    }

    private fun notCrawlerEvent(url: String) {
        entity.visitedLinks.add(url)
        startCrawler()
    }
}