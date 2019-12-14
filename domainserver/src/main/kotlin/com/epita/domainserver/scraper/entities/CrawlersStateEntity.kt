package com.epita.domainserver.scraper.entities

import java.util.concurrent.ConcurrentLinkedQueue

class CrawlersStateEntity {
    val visitedLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val toVisitLinks: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    val crawlerList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
}