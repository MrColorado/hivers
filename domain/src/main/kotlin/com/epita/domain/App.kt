package com.epita.domain

import com.epita.brokerclient.client.BrokerClient
import com.epita.domain.scraper.Scraper
import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.cleaner.impl.HtmlCleaner
import com.epita.domain.tfidf.cleaner.subscribers.CleanerSubscriber
import com.epita.hivers.core.Hivers
import com.epita.models.BrokerClientInterface
import com.epita.models.Publisher

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
        bean(CleanerServiceInterface::class.java, HtmlCleaner())
    }

    val publisher = Publisher(hivers.instanceOf(BrokerClientInterface::class.java))
    val scraper = Scraper(hivers.instanceOf(BrokerClientInterface::class.java), publisher)
    CleanerSubscriber(hivers.instanceOf(BrokerClientInterface::class.java), "crawled-event",
        publisher, hivers.instanceOf(CleanerServiceInterface::class.java))


    scraper.start()
}