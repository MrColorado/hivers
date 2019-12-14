package com.epita.index.impl

import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.utils.Calculus
import com.epita.domain.tfidf.utils.IO
import com.epita.domain.tfidf.utils.IdfCalculator
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.index.core.IndexServiceInterface
import com.epita.index.models.RetroIndexModel
import com.epita.models.tfidf.Cleaned
import com.epita.models.tfidf.DocumentCosine
import com.epita.models.tfidf.TfIdfByWord
import com.epita.models.tfidf.Vectorized
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.*
import java.util.concurrent.ConcurrentHashMap

class RetroIndex(val tokenizerService: TokenizerServiceInterface, val vectorizerService: VectorizerServiceInterface) :
    IndexServiceInterface {
    // {url:document}
    private var indexedDocuments: ConcurrentHashMap<String, Vectorized> = ConcurrentHashMap()
    // {keyword:[urls]}
    private var urlsForKeyword: ConcurrentHashMap<String, MutableSet<String>> = ConcurrentHashMap()
    private val fiveMin: Long = 1000 * 5

    init {
        /*
        val retroIndex = importIndex()
        indexedDocuments = retroIndex.indexedDocuments
        urlsForKeyword = retroIndex.urlsForKeyword
        */
        Thread {
            while (true) {
                Thread.sleep(fiveMin)
                exportIndex()
            }
        }.run()
    }

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

    private fun search(query: Vectorized): List<String> {
        val matchingDocuments = query.keywords
            .flatMap { search(it.key) }
            .map { indexedDocuments[it]!! }
            .map { Vectorized(it.url, it.keywords.filter { k -> query.keywords.containsKey(k.key) }) }
            .toHashSet()

        val queryVector = Calculus.normalize(idfVector(query, matchingDocuments.size))
        val documentListVector =
            matchingDocuments.map { Pair(it.url, Calculus.normalize(idfVector(it, matchingDocuments.size))) }

        return documentListVector.map { DocumentCosine(it.first, Calculus.cosineSimilarity(queryVector, it.second)) }
            .sortedByDescending { it.cosine }.map { it.url }
    }

    override fun query(query: String): List<String> {
        val tokenized = tokenizerService.compute(Cleaned(query, query))
        val vectorized = vectorizerService.compute(tokenized)
        return search(vectorized)
    }

    override fun getCorpusSize(): Int {
        return indexedDocuments.size
    }

    private fun idfVector(vector: Vectorized, matchingSize: Int): List<TfIdfByWord> {
        return vector.keywords.map { (k, v) ->
            TfIdfByWord(
                v.frequency * IdfCalculator.compute(
                    getCorpusSize(),
                    matchingSize
                ), k
            )
        }.toList()
    }

    private fun exportIndex() {
        val mapper = jacksonObjectMapper()
        val retroIndex = RetroIndexModel(indexedDocuments, urlsForKeyword)
        val serialized = mapper.writeValueAsString(retroIndex)
        ObjectOutputStream(FileOutputStream("retro_index_data")).use{ it -> it.writeObject(serialized)}
    }

    private fun importIndex() : RetroIndexModel {
        val mapper = jacksonObjectMapper()
        val file = File("retro_index_data").readText()
        val retroIndex = mapper.readValue(file, RetroIndexModel::class.java)
        return retroIndex
    }

}