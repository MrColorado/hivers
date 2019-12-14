package com.epita.domainserver.tfidf

import com.epita.domainserver.tfidf.models.IndexerAvailability
import com.epita.domainserver.tfidf.subscribers.IndexedDocumentSubscriber
import com.epita.domainserver.tfidf.subscribers.RegisterCrawledSubscriber
import com.epita.domainserver.tfidf.subscribers.RegisterIndexerSubscriber
import com.epita.models.commands.IndexCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.tfidf.DocumentWithUrl
import java.util.concurrent.ConcurrentLinkedQueue

class IndexerOrchestrator(val brokerClient: BrokerClientInterface, val publisher: Publisher) {
    private val documentToIndex: ConcurrentLinkedQueue<DocumentWithUrl> = ConcurrentLinkedQueue()
    private val indexerList: ConcurrentLinkedQueue<IndexerAvailability> = ConcurrentLinkedQueue()
    private var availableIndexer = 0

    fun start() {
        IndexedDocumentSubscriber(brokerClient, "indexed-document-event") { id -> availableIndexer(id) }
        RegisterCrawledSubscriber(brokerClient, "crawled-event") { document ->  registerDocument(document) }
        RegisterIndexerSubscriber(brokerClient, "indexer-init-command") { url -> registerIndexer(url) }
    }

    private fun dispatchDocument() {
        if (availableIndexer <= 0)
            return
        val crawledDocument = documentToIndex.first() ?: return

        availableIndexer -= 1
        documentToIndex.remove(crawledDocument)

        publisher.publish("index-document-command", IndexCommand(crawledDocument.url, crawledDocument.text),
            MessageType.ONCE, IndexCommand::class.java)
    }

    private fun registerDocument(document: DocumentWithUrl) {
        documentToIndex.add(document)
        dispatchDocument()
    }

    private fun registerIndexer(id: String) {
        indexerList.add(IndexerAvailability(id, true))
        availableIndexer += 1
        dispatchDocument()
    }

    private fun availableIndexer(id: String) {
        availableIndexer += 1
        dispatchDocument()
    }
}