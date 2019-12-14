package com.epita.domainserver.tfidf

import com.epita.domainserver.tfidf.subscribers.DispatchCrawledSubscriber
import com.epita.domainserver.tfidf.subscribers.RegisterCrawledSubscriber
import com.epita.domainserver.tfidf.subscribers.RegisterIndexerSubscriber
import com.epita.models.commands.IndexCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.tfidf.DocumentWithUrl
import java.util.concurrent.ConcurrentLinkedQueue

class IndexerOrchestrator(val brokerClient: BrokerClientInterface, val publisher: Publisher) {
    val documentToIndex: ConcurrentLinkedQueue<DocumentWithUrl> = ConcurrentLinkedQueue()
    val indexerList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()

    fun start() {
        DispatchCrawledSubscriber(brokerClient, "indexed-document-event") { dispatchDocument() }
        RegisterCrawledSubscriber(brokerClient, "crawled-event") { document ->  registerDocument(document) }
        RegisterIndexerSubscriber(brokerClient, "indexer-init-event") { url -> registerIndexer(url) }
    }

    private fun dispatchDocument() {
        // TODO HANDLE 0 MASSAGE
        if (documentToIndex.size > 0) {
            val crawledDocument = documentToIndex.first()
            documentToIndex.remove(crawledDocument)
            publisher.publish("index-document-command", IndexCommand(crawledDocument.url, crawledDocument.text),
                MessageType.ONCE, IndexCommand::class.java)
        }
    }

    private fun registerDocument(document: DocumentWithUrl) {
        documentToIndex.add(document)
    }

    private fun registerIndexer(url: String) {
        indexerList.add(url)
        dispatchDocument()
    }

}