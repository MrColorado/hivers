package com.epita.domainserver.tfidf.impl

import com.epita.domainserver.tfidf.core.IndexerOrchestratorServiceInterface
import com.epita.domainserver.tfidf.entities.IndexersStateEntity
import com.epita.domainserver.tfidf.models.IndexerAvailability
import com.epita.domainserver.tfidf.subscribers.CrawledEventSubscriber
import com.epita.domainserver.tfidf.subscribers.IndexedDocumentEventSubscriber
import com.epita.domainserver.tfidf.subscribers.IndexerInitCommandSubscriber
import com.epita.models.Topics
import com.epita.models.commands.IndexCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.PublisherInterface
import com.epita.models.tfidf.DocumentWithUrl

class IndexerOrchestratorService(
    private val brokerClient: BrokerClientInterface,
    private val publisher: PublisherInterface,
    private val entity: IndexersStateEntity = IndexersStateEntity()
) :
    IndexerOrchestratorServiceInterface {

    override fun start() {
        IndexedDocumentEventSubscriber(brokerClient, Topics.INDEXED_DOCUMENT_EVENT.str) { id -> availableIndexer(id) }
        CrawledEventSubscriber(brokerClient, Topics.CRAWLED_EVENT.str) { document -> registerDocument(document) }
        IndexerInitCommandSubscriber(brokerClient, Topics.INDEXER_INIT_COMMAND.str) { url -> registerIndexer(url) }
    }

    private fun dispatchDocument() {
        if (entity.availableIndexer <= 0)
            return
        val crawledDocument = entity.documentToIndex.first() ?: return

        entity.availableIndexer -= 1
        entity.documentToIndex.remove(crawledDocument)

        publisher.publish(
            Topics.INDEX_DOCUMENT_COMMAND.str,
            IndexCommand(crawledDocument.url, crawledDocument.text),
            MessageType.ONCE, IndexCommand::class.java
        )
    }

    private fun registerDocument(document: DocumentWithUrl) {
        entity.documentToIndex.add(document)
        dispatchDocument()
    }

    private fun registerIndexer(id: String) {
        entity.indexerList.add(IndexerAvailability(id, true))
        entity.availableIndexer += 1
        dispatchDocument()
    }

    private fun availableIndexer(id: String) {
        entity.availableIndexer += 1
        dispatchDocument()
    }
}