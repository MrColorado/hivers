package com.epita.tfidf.scraper.core

interface Crawler : Runnable{
    var recursive: Boolean
}