package com.epita.tfidf.scraper.impl

import com.epita.crawler.Constants
import com.epita.crawler.impl.BasicCrawler
import com.epita.tfidf.scraper.core.Scraper
import org.jsoup.nodes.Document
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BasicScraper : Scraper {
    companion object {
        val visitedLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
        val toVisitLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
        val scrapedDocument : ConcurrentLinkedQueue<Document> = ConcurrentLinkedQueue()
    }

    override fun startCrawlers(recursive: Boolean) {
        toVisitLinks.addAll(Constants.urls)
        val executor = Executors.newFixedThreadPool(5)
        for (i in 0..1) {
            val worker = BasicCrawler(recursive)
            executor.execute(worker)
        }

        // TODO handle proper wait
        executor.shutdown()
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        } catch (e: InterruptedException) {
            println(e)
        }
    }
}