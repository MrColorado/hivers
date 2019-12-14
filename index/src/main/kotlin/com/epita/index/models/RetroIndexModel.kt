package com.epita.index.models

import com.epita.models.tfidf.Vectorized
import java.util.concurrent.ConcurrentHashMap

data class RetroIndexModel(val indexedDocuments: ConcurrentHashMap<String, Vectorized>,
                           val urlsForKeyword: ConcurrentHashMap<String, MutableSet<String>>)