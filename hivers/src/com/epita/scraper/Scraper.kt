package com.epita.scraper

import org.jsoup.nodes.Document
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class Scraper {
    companion object {
        val visitedLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
        val toVisitLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
        val scrapedDocument : ConcurrentLinkedQueue<Document> = ConcurrentLinkedQueue()
    }

    fun startCrawlers(recursive: Boolean = true) {
        toVisitLinks.addAll(Constants.urls)
        val executor = Executors.newFixedThreadPool(5)
        for (i in 0..1) {
            val worker = Crawler(recursive)
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