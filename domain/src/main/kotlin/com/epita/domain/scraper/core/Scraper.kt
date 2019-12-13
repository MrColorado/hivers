package com.epita.domain.scraper.core

interface Scraper {
    fun startCrawlers(recursive: Boolean = true)
}