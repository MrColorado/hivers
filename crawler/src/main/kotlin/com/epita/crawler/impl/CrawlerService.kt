package com.epita.crawler.impl

import com.epita.crawler.core.CrawlerServiceInterface
import com.epita.models.Topics
import com.epita.models.communications.MessageType
import com.epita.models.communications.PublisherInterface
import com.epita.models.events.CrawledEvent
import com.epita.models.events.NotCrawledEvent
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.regex.Pattern

class CrawlerService(private val publisher: PublisherInterface) : CrawlerServiceInterface {
    companion object {
        private const val REGEX_DOMAIN_NAME = "[^/]/[^/]"
        private const val TIMEOUT = 5000
    }

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun crawl(url: String) {
        val pattern = Pattern.compile(REGEX_DOMAIN_NAME)
        val matcher = pattern.matcher(url)
        val index = if (!matcher.find()) {
            url.length
        } else {
            matcher.start() + 1
        }
        val rootUrl = url.substring(0, index)
        try {
            Jsoup.connect(url).timeout(TIMEOUT).get().run {
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
                publisher.publish(Topics.CRAWLED_EVENT.str, crawledEvent, MessageType.BROADCAST, CrawledEvent::class.java)
            }
        }
        catch (e : Exception) {
            logger.error("Cannot parse url: $url")
            val notCrawledEvent = NotCrawledEvent(url)
            publisher.publish(Topics.NOT_CRAWLED_EVENT.str, notCrawledEvent, MessageType.BROADCAST, NotCrawledEvent::class.java)
        }
    }
}