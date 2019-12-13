package com.epita.index.impl

import com.epita.tfidf.idf.impl.IdfCalculator
import com.epita.index.core.Index
import com.epita.tfidf.models.DocumentCosine
import com.epita.tfidf.models.TfIdfByWord
import com.epita.tfidf.models.Vectorized
import com.epita.tfidf.utils.Calculus
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

    override fun search(query: Vectorized) : List<DocumentCosine> {
        val matchingDocuments = query.keywords
                .flatMap { search(it.key) }
                .map { indexedDocuments[it]!! }
                .map { Vectorized(it.url, it.keywords.filter { k -> query.keywords.containsKey(k.key) }) }
                .toHashSet()

        val queryVector = Calculus.normalize(idfVector(query, matchingDocuments.size))
        val documentListVector = matchingDocuments.map { Pair(it.url, Calculus.normalize(idfVector(it, matchingDocuments.size))) }

        return documentListVector.map { DocumentCosine(it.first, Calculus.cosineSimilarity(queryVector, it.second)) }
                .sortedBy { it.cosine }
    }

    override fun getCorpusSize(): Int {
        return indexedDocuments.size
    }

    fun idfVector(vector: Vectorized, matchingSize: Int) : List<TfIdfByWord> {
        return vector.keywords.map { (k, v) -> TfIdfByWord(v.frequency * IdfCalculator.compute(getCorpusSize(), matchingSize), k) }.toList()
    }

}