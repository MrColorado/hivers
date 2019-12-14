package com.epita.index.impl

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.utils.Calculus
import com.epita.domain.tfidf.utils.IdfCalculator
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.index.core.IndexServiceInterface
import com.epita.models.tfidf.Cleaned
import com.epita.models.tfidf.DocumentCosine
import com.epita.models.tfidf.TfIdfByWord
import com.epita.models.tfidf.Vectorized
import java.util.concurrent.ConcurrentHashMap

class RetroIndex(val tokenizerService: TokenizerServiceInterface, val vectorizerService: VectorizerServiceInterface) : IndexServiceInterface {
    // {url:document}
    private val indexedDocuments: ConcurrentHashMap<String, Vectorized> = ConcurrentHashMap()
    // {keyword:[urls]}
    private val urlsForKeyword: ConcurrentHashMap<String, MutableSet<String>> = ConcurrentHashMap()

    private fun insert(keyword: String, document: Vectorized) {
        val urls = urlsForKeyword.getOrPut(keyword) { mutableSetOf() }
        urls.add(document.url)
    }

    override fun index(document: Vectorized) {
        indexedDocuments[document.url] = document
        document.keywords.forEach { (key, _) -> insert(key, document) }
    }

    private fun search(keyword: String): Set<String> {
        return urlsForKeyword.getOrDefault(keyword, mutableSetOf())
    }

    private fun search(query: Vectorized) : List<DocumentCosine> {
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

    override fun query(query: String) : List<DocumentCosine> {
        val tokenized = tokenizerService.compute(Cleaned(query, query))
        val vectorized = vectorizerService.compute(tokenized)
        return search(vectorized)
    }

    override fun getCorpusSize(): Int {
        return indexedDocuments.size
    }

    private fun idfVector(vector: Vectorized, matchingSize: Int) : List<TfIdfByWord> {
        return vector.keywords.map { (k, v) -> TfIdfByWord(v.frequency * IdfCalculator.compute(getCorpusSize(), matchingSize), k) }.toList()
    }

}