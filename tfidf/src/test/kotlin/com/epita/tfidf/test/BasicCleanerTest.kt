package com.epita.tfidf.test

import com.epita.tfidf.cleaner.impl.HtmlCleaner
import org.jsoup.Jsoup
import org.junit.Test
class BasicCleanerTest {
    @Test
    fun `test basic cleaner use case`() {
        val htmlCleaner = HtmlCleaner()
        val doc = Jsoup.connect("http://en.wikipedia.org/").get();
        val cleaned = htmlCleaner.compute(doc)
    }
}