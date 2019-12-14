package com.epita.crawler.impl

import com.epita.crawler.core.CrawlerServiceInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.PublisherInterface
import com.epita.models.events.CrawledEvent
import com.epita.models.events.NotCrawledEvent
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.regex.Pattern

class CrawlerService(private val publisher: PublisherInterface) : CrawlerServiceInterface {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun crawl(url: String) {
        val pattern = Pattern.compile("[^/]/[^/]")
        val matcher = pattern.matcher(url)
        val index = if (!matcher.find()) {
            url.length
        } else {
            matcher.start() + 1
        }
        val rootUrl = url.substring(0, index)
        try {
            Jsoup.connect(url).timeout(5000).get().run {
                this.body().text()
                val links = this.select("a[href]")
                val hrefList = ArrayList<String>()
                for (link in links) {
                    var href = link.attr("href")
                    if (!href.contains("http"))
                        href = rootUrl + href
                    hrefList.add(href)
                }
                val crawledEvent = CrawledEvent(url, this.text(), hrefList)
                publisher.publish("crawled-event", crawledEvent, MessageType.BROADCAST, CrawledEvent::class.java)
            }
        }
        catch (e : Exception) {
            logger.error("Cannot parse url: $url")
            val notCrawledEvent = NotCrawledEvent(url)
            publisher.publish("not-crawled-event", notCrawledEvent, MessageType.BROADCAST, NotCrawledEvent::class.java)
        }
    }
}