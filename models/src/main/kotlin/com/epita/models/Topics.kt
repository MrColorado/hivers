package com.epita.models

enum class Topics(val str: String) {
    CRAWLER_INIT_COMMAND("crawler-init-command"),
    CRAWLED_EVENT("crawled-event"),
    NOT_CRAWLED_EVENT("not-crawled-event"),
    CRAWL_URL_COMMAND("crawl-url-command"),
    INDEXED_DOCUMENT_EVENT("indexed-document-event"),
    INDEXER_INIT_COMMAND("indexer-init-command"),
    INDEX_DOCUMENT_COMMAND("index-document-command"),
    ;
}