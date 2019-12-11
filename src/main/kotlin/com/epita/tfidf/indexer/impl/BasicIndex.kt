package com.epita.tfidf.indexer.impl

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
        return query.keywords
                .flatMap { search(it.key) }
                .map { url -> indexedDocuments[url]!! }.toHashSet()
    }

    override fun getCorpusSize(): Int {
        return indexedDocuments.size
    }

}