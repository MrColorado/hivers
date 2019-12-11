package com.epita.tfidf.test

import com.epita.tfidf.cleaner.impl.HtmlCleaner
import com.epita.tfidf.indexer.impl.BasicIndex
import com.epita.tfidf.indexer.impl.BasicIndexer
import com.epita.tfidf.models.Cleaned
import com.epita.tfidf.models.KeywordData
import com.epita.tfidf.models.Vectorized
import com.epita.tfidf.tokenizer.impl.BasicTokenizer
import com.epita.tfidf.vectorizer.impl.BasicVectorizer
import org.jsoup.Jsoup
import org.junit.Test
class BasicIndexTest {
    @Test
    fun `test basic index use case`() {
        val index = BasicIndex()
        val indexer = BasicIndexer(index)
        /*
        val map = HashMap<String, KeywordData>()
        map["key"] = KeywordData(4.2, arrayListOf(1,2))
        val document = Vectorized("https://sample.com/url", map)
        indexer.indexDocument(document)
        */


        val htmlCleaner = HtmlCleaner()
        val doc = Jsoup.connect("http://en.wikipedia.org/").get();
        val cleaned = htmlCleaner.compute(doc)

        val tokenizer = BasicTokenizer()

        val tokenizedDocument = tokenizer.compute(cleaned)

        val tokenizedQuery = tokenizer.compute(Cleaned("", "wikipedia wikipedia"))

        val vectorizer = BasicVectorizer()

        val vectorizedDocument = vectorizer.compute(tokenizedDocument)
        val vectorizedQuery = vectorizer.compute(tokenizedQuery)
        indexer.indexDocument(vectorizedDocument)
        val result = index.search(vectorizedQuery)

    }

    @Test
    fun `test integrated index use case`() {
        val index = BasicIndex()
        val indexer = BasicIndexer(index)

        val htmlCleaner = HtmlCleaner()
        val doc = Jsoup.connect("http://en.wikipedia.org/").get();
        val cleaned = htmlCleaner.compute(doc)

        val tokenizer = BasicTokenizer()

        val tokenizedDocument = tokenizer.compute(cleaned)

        var tokenizedQuery = tokenizer.compute(Cleaned("", "wikipedia wikipedia"))

        val vectorizer = BasicVectorizer()

        val vectorizedDocument = vectorizer.compute(tokenizedDocument)
        var vectorizedQuery = vectorizer.compute(tokenizedQuery)
        indexer.indexDocument(vectorizedDocument)
        var result = index.search(vectorizedQuery)

        assert(result.size == 1)

        tokenizedQuery = tokenizer.compute(Cleaned("", "vincent"))
        vectorizedQuery = vectorizer.compute(tokenizedQuery)
        result = index.search(vectorizedQuery)

        assert(result.isEmpty())
    }
}