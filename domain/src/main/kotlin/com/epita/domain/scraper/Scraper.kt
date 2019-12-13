package com.epita.domain.scraper

import com.epita.domain.scraper.subscribers.CrawlerInitSubscriber
import com.epita.models.BrokerClientInterface
import com.epita.models.Publisher
import java.util.concurrent.ConcurrentLinkedQueue

class Scraper(val brokerClient: BrokerClientInterface, val publisher: Publisher) {
    val visitedLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val toVisitLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val crawlerList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()

    fun start() {
        toVisitLinks.addAll(Constants.urls)
        CrawlerInitSubscriber(brokerClient, "crawler-init") { id -> initCrawler(id) }
    }

    private fun initCrawler(id: String) {
        crawlerList.add(id)
        println(id)
    }
}