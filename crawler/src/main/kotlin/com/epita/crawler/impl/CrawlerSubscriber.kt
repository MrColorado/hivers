package com.epita.crawler.impl

import com.epita.models.BrokerClientInterface
import com.epita.models.MessageType
import com.epita.models.Publisher
import com.epita.models.Subscriber
import com.epita.models.commands.CrawlerCommand
import com.epita.models.events.CrawledEvent
import org.jsoup.Jsoup
import java.lang.Exception
import java.util.regex.Pattern


class CrawlerSubscriber : Subscriber {

    val publisher: Publisher

    constructor(brokerClient: BrokerClientInterface, topic: String, publisher: Publisher) : super(brokerClient, topic) {
        init()
        this.publisher = publisher
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawlerCommand = message as CrawlerCommand
        val url = crawlerCommand.url
        val pattern = Pattern.compile("[^/]/[^/]")
        val matcher = pattern.matcher(url)
        if (!matcher.find())
            return
        val index = matcher.start() + 1
        val rootUrl = url.substring(0, index)
        try {
            Jsoup.connect(url).get().run {
                this.body().text()
                val links = this.select("a[href]")
                val hrefList = ArrayList<String>()
                for (link in links) {
                    var href = link.attr("href")
                    if (!href.contains("http"))
                        href = rootUrl + href
                    hrefList.add(href)
                }
                val crawledEvent = CrawledEvent(url, this, hrefList)
                publisher.publish("crawled-event", crawledEvent, MessageType.BROADCAST, CrawledEvent::class.java)
            }
        }
        catch (e : Exception) {
            // TODO log
            println("Cannot parse url: $url")
        }
    }
}