package com.epita.tfidf.indexer.impl

import com.epita.tfidf.indexer.core.Index
import com.epita.tfidf.indexer.core.Indexer
import com.epita.tfidf.models.Vectorized
import java.util.concurrent.ConcurrentHashMap

class BasicIndex() : Index{

    private val indexedDocuments: ConcurrentHashMap<String, ArrayList<Vectorized>> = ConcurrentHashMap()

    override fun insert(keyword: String, document: Vectorized) {
        val documents = indexedDocuments[keyword]
        if (null == documents)
            indexedDocuments[keyword] = arrayListOf(document)
        else {
            documents.add(document)
            indexedDocuments[keyword] = documents
        }
    }

    override fun search(keyword: String): List<Vectorized>? {
        return indexedDocuments[keyword]
    }

}