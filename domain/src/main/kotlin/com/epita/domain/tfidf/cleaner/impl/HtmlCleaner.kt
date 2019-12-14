package com.epita.domain.tfidf.cleaner.impl

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.models.tfidf.Cleaned
import com.epita.models.tfidf.DocumentWithUrl

class HtmlCleaner : CleanerServiceInterface {
    override fun compute(document: DocumentWithUrl): Cleaned {
        return Cleaned(document.url, document.text.toLowerCase())
    }
}