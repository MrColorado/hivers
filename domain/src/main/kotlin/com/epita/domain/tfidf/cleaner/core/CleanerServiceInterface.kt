package com.epita.domain.tfidf.cleaner.core

import com.epita.domain.tfidf.models.Cleaned
import com.epita.models.tfidf.DocumentWithUrl

interface CleanerServiceInterface {
    fun compute(document: DocumentWithUrl) : Cleaned
}