package com.epita.domain.tfidf.test

import com.epita.domain.tfidf.cleaner.impl.HtmlCleaner
import com.epita.models.tfidf.DocumentWithUrl
import org.jsoup.Jsoup
import org.junit.Test
class BasicCleanerTest {
    @Test
    fun `test basic cleaner use case`() {
        val htmlCleaner = HtmlCleaner()
        val doc = Jsoup.connect("http://en.wikipedia.org/").get();
        val cleaned = htmlCleaner.compute(DocumentWithUrl(doc.baseUri(), doc.text()))
    }
}