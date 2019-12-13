package com.epita.domain.tfidf.cleaner.core

import com.epita.domain.tfidf.models.Cleaned
import org.jsoup.nodes.Document

interface Cleaner {
    fun compute(document: Document) : Cleaned
}