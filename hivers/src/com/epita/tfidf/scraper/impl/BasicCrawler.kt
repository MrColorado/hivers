package com.epita.tfidf.scraper.impl

import com.epita.tfidf.scraper.core.Crawler
import org.jsoup.Jsoup
import java.lang.Exception
import java.util.regex.Pattern


class BasicCrawler(override var recursive: Boolean = true) : Crawler {

    override fun run() {
        do {
            val url = BasicScraper.toVisitLinks.remove()
            if (!BasicScraper.visitedLinks.contains(url)) {
                val pattern = Pattern.compile("[^/]/[^/]")
                val matcher = pattern.matcher(url)
                if (!matcher.find())
                    continue
                val index = matcher.start() + 1
                val rootUrl = url.substring(0, index)
                try {
                    Jsoup.connect(url).get().run {
                        // TODO Handle document
                        BasicScraper.scrapedDocument.add(this)
                        val links = this.select("a[href]")
                        for (link in links) {
                            var href = link.attr("href")
                            if (!href.contains("http"))
                                href = rootUrl + href
                            BasicScraper.toVisitLinks.add(href)
                        }
                    }
                }
                catch (e : Exception) {
                    // TODO log
                    println("Cannot parse url: $url")
                }
                BasicScraper.visitedLinks.add(url)
            }
        } while (BasicScraper.toVisitLinks.size > 0 && recursive)
    }
}