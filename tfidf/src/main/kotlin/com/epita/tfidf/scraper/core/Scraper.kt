package com.epita.tfidf.scraper.core

interface Scraper {
    fun startCrawlers(recursive: Boolean = true)
}