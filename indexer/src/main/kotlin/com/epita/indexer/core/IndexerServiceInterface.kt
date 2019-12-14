package com.epita.indexer.core

import com.epita.models.tfidf.DocumentWithUrl

interface IndexerServiceInterface {
    fun index(document: DocumentWithUrl)
}