package com.epita.indexer.subscribers

import com.epita.indexer.core.IndexerServiceInterface
import com.epita.models.commands.IndexCommand
import com.epita.models.communications.*
import com.epita.models.tfidf.DocumentWithUrl

class IndexDocumentCommandSubscriber : Subscriber {

    private val service: IndexerServiceInterface

    constructor(brokerClient: BrokerClientInterface, topic: String, service: IndexerServiceInterface) : super(brokerClient, topic) {
        init()
        this.service = service
    }

    override fun <CLASS> handle(message: CLASS) {
        val indexDocument = message as IndexCommand
        val document = DocumentWithUrl(indexDocument.url, indexDocument.document)
        service.index(document)
    }
}