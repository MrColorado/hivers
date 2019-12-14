package com.epita.domainserver.tfidf.entities

import com.epita.domainserver.tfidf.models.IndexerAvailability
import com.epita.models.tfidf.DocumentWithUrl
import java.util.concurrent.ConcurrentLinkedQueue

class IndexersStateEntity {
    val documentToIndex: ConcurrentLinkedQueue<DocumentWithUrl> = ConcurrentLinkedQueue()
    val indexerList: ConcurrentLinkedQueue<IndexerAvailability> = ConcurrentLinkedQueue()
    var availableIndexer = 0
}