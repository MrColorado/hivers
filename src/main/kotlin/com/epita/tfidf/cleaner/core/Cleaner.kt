package com.epita.tfidf.cleaner.core

import com.epita.tfidf.models.Cleaned
import org.jsoup.nodes.Document

interface Cleaner {
    fun compute(document: Document) : Cleaned
}