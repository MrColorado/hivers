package com.epita.models.events

import com.epita.models.tfidf.Vectorized

data class IndexedEvent(val id: String, val document: Vectorized)