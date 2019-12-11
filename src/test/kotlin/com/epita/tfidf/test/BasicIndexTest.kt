package com.epita.tfidf.test

import com.epita.tfidf.indexer.impl.BasicIndex
import com.epita.tfidf.indexer.impl.BasicIndexer
import com.epita.tfidf.models.KeywordData
import com.epita.tfidf.models.Vectorized
import org.junit.Test
class BasicIndexTest {
    @Test
    fun `test basic index use case`() {
        val index = BasicIndex()
        val indexer = BasicIndexer(index)
        val map = HashMap<String, KeywordData>()
        map["key"] = KeywordData(4.2, arrayListOf(1,2))
        val document = Vectorized("https://sample.com/url", map)
        indexer.indexDocument(document)
        println(index.search("key"))
    }
}