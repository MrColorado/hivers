package com.epita.scraper

import org.jsoup.Jsoup
import java.lang.Exception
import java.util.regex.Pattern


class Crawler : Runnable {

    private var recursive: Boolean

    constructor(recursive: Boolean) {
        this.recursive = recursive
    }

    constructor() {
        this.recursive = true
    }


    override fun run() {
        do {
            val url = Scraper.toVisitLinks.remove()
            if (!Scraper.visitedLinks.contains(url)) {
                val pattern = Pattern.compile("[^/]/[^/]")
                val matcher = pattern.matcher(url)
                if (!matcher.find())
                    continue
                val index = matcher.start() + 1
                val rootUrl = url.substring(0, index)
                try {
                    Jsoup.connect(url).get().run {
                        // TODO Handle document
                        Scraper.scrapedDocument.add(this)
                        val links = this.select("a[href]")
                        for (link in links) {
                            var href = link.attr("href")
                            if (!href.contains("http"))
                                href = rootUrl + href
                            Scraper.toVisitLinks.add(href)
                        }
                    }
                }
                catch (e : Exception) {
                    // TODO log
                    println("Cannot parse url: $url")
                }
                Scraper.visitedLinks.add(url)
            }
        } while (Scraper.toVisitLinks.size > 0 && recursive)
    }
}