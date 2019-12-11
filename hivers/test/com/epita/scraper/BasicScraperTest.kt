package com.epita.scraper
import org.junit.Test

class BasicScraperTest {

    @Test
    fun `test basic scraper use case`() {
        val scraper = Scraper()
        scraper.startCrawlers(false)
        // Wait a little to scrap
        Thread.sleep(10)
        val document = Scraper.scrapedDocument.peek()
        assert(null != document)
    }
}