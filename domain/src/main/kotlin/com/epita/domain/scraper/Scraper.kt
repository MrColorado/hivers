package com.epita.domain.scraper

import com.epita.domain.scraper.subscribers.CrawledSubscriber
import com.epita.domain.scraper.subscribers.CrawlerInitSubscriber
import com.epita.models.BrokerClientInterface
import com.epita.models.MessageType
import com.epita.models.Publisher
import com.epita.models.commands.CrawlerCommand
import com.epita.models.events.CrawledEvent
import java.util.concurrent.ConcurrentLinkedQueue

class Scraper(val brokerClient: BrokerClientInterface, val publisher: Publisher) {
    val visitedLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val toVisitLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val crawlerList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()

    fun start() {
        toVisitLinks.addAll(Constants.urls)
        CrawlerInitSubscriber(brokerClient, "crawler-init") { id -> initCrawler(id) }
        CrawledSubscriber(brokerClient, "crawled-event") { url, urls ->  crawledEvent(url, urls) }
        CrawlerInitSubscriber(brokerClient, "not-crawled-event") { url -> notCrawlerEvent(url) }
    }

    private fun startCrawler() {
        var url = toVisitLinks.remove()
        while (visitedLinks.contains(url))
            url = toVisitLinks.remove()
        publisher.publish("crawl-url", CrawlerCommand(url), MessageType.ONCE, CrawlerCommand::class.java)
    }

    private fun initCrawler(id: String) {
        crawlerList.add(id)
        startCrawler()
    }

    private fun crawledEvent(url: String, urls: List<String>) {
        visitedLinks.add(url)
        toVisitLinks.addAll(urls)
        startCrawler()
    }

    private fun notCrawlerEvent(url: String) {
        visitedLinks.add(url)
        startCrawler()
    }
}