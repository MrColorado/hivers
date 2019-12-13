package com.epita.domain.tfidf.cleaner.impl

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.models.Cleaned
import com.epita.domain.tfidf.models.DocumentWithUrl

class HtmlCleaner : CleanerServiceInterface {
    override fun compute(document: DocumentWithUrl): Cleaned {
        return Cleaned(document.url, document.text.toLowerCase())
    }
}