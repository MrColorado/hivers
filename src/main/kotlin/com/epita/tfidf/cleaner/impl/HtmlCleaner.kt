package com.epita.tfidf.cleaner.impl

import com.epita.tfidf.cleaner.core.Cleaner
import com.epita.tfidf.models.Cleaned
import org.jsoup.nodes.Document

class HtmlCleaner : Cleaner {
    override fun compute(document: Document): Cleaned {
        return Cleaned(document.baseUri(), document.text())
    }
}