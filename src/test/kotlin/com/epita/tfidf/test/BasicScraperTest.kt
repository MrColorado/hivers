package com.epita.tfidf.test

import com.epita.tfidf.scraper.impl.BasicScraper
import org.junit.Test
class BasicScraperTest {
    @Test
    fun `test basic scraper use case`() {
        val scraper = BasicScraper()
        scraper.startCrawlers(false)
        // Wait a little to scrap
        Thread.sleep(10)
        val document = BasicScraper.scrapedDocument.peek()
        assert(null != document)
    }
}