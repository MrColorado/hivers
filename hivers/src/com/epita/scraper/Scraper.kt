package com.epita.scraper

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class Scraper {
    companion object {
        val visitedLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
        val toVisitLinks : ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    }

    fun startCrawlers() {
        toVisitLinks.add("https://en.wikipedia.org/wiki/Main_Page")
        toVisitLinks.add("https://medium.com/")
        val executor = Executors.newFixedThreadPool(5)
        for (i in 0..1) {
            val worker = Crawler()
            executor.execute(worker)
        }
        while(!executor.isTerminated) {
            continue
        }
        executor.shutdown()
    }
}