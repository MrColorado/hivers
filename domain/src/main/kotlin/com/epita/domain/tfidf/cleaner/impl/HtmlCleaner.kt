package com.epita.domain.tfidf.cleaner.impl

import com.epita.domain.tfidf.cleaner.core.Cleaner
import com.epita.domain.tfidf.models.Cleaned
import org.jsoup.nodes.Document

class HtmlCleaner : Cleaner {
    override fun compute(document: Document): Cleaned {
        return Cleaned(document.baseUri(), document.text().toLowerCase())
    }
}