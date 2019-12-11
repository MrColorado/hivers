package com.epita.tfidf.indexer.impl

import com.epita.tfidf.idf.impl.IdfCalculator
import com.epita.tfidf.indexer.core.Index
import com.epita.tfidf.indexer.core.Indexer
import com.epita.tfidf.models.Vectorized
import java.util.concurrent.ConcurrentHashMap

class BasicIndex : Index {
    // {url:document}
    private val indexedDocuments: ConcurrentHashMap<String, Vectorized> = ConcurrentHashMap()
    // {keyword:[urls]}
    private val urlsForKeyword: ConcurrentHashMap<String, MutableSet<String>> = ConcurrentHashMap()

    override fun insert(keyword: String, document: Vectorized) {
        val urls = urlsForKeyword.getOrPut(keyword) { mutableSetOf() }
        urls.add(document.url)
    }

    override fun index(document: Vectorized) {
        indexedDocuments[document.url] = document
    }

    private fun search(keyword: String): Set<String> {
        return urlsForKeyword.getOrDefault(keyword, mutableSetOf())
    }

    override fun search(query: Vectorized) : Set<Vectorized> {
        val matchingDocuments = query.keywords
                .flatMap { search(it.key) }
                .map { indexedDocuments[it]!! }
                .map { Vectorized(it.url, it.keywords.filter { k -> query.keywords.containsKey(k.key) }) }
                .toHashSet()

        val queryVector = idfVector(query, matchingDocuments.size)
        val documentListVector = matchingDocuments.map { idfVector(it, matchingDocuments.size) }

        return matchingDocuments
    }

    override fun getCorpusSize(): Int {
        return indexedDocuments.size
    }

    fun idfVector(vector: Vectorized, matchingSize: Int) : List<Pair<String, Double>> {
        return vector.keywords.map { (k, v) -> Pair(k, v.frequency * IdfCalculator.compute(getCorpusSize(), matchingSize)) }.toList()
    }

}