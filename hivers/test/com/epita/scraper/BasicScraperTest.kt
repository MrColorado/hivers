package com.epita.scraper
import org.junit.Test

class BasicScraperTest {

    @Test
    fun `test basic scraper use case`() {
        val scraper = Scraper()
        scraper.startCrawlers()
    }
}