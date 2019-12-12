package com.epita.tfidf.test

import com.epita.tfidf.models.TfIdfByWord
import com.epita.tfidf.utils.Calculus
import org.junit.Test
import kotlin.math.abs

class CalculusTest {

    @Test
    fun normalizeBasicTest() {
        val query = listOf(TfIdfByWord(15.0, "ciel"), TfIdfByWord(13.0, "bleu"), TfIdfByWord(5.0, "chien"))
        val res = listOf(TfIdfByWord(0.7328, "ciel"), TfIdfByWord(0.6351, "bleu"), TfIdfByWord(0.24427, "chien"))
        val normalized = Calculus.normalize(query)

        res.zip(normalized).map { assert(abs(it.first.tfidf - it.second.tfidf) < 0.001) }
    }

    @Test
    fun cosineSimilarityBasicTest() {
        val query = listOf(TfIdfByWord(15.0, "ciel"), TfIdfByWord(13.0, "bleu"), TfIdfByWord(5.0, "chien"))
        val document = listOf(TfIdfByWord(15.0, "ciel"), TfIdfByWord(13.0, "bleu"), TfIdfByWord(5.0, "chien"))
        assert(Calculus.cosineSimilarity(query, document) - 419 < 0.000001)
    }

    @Test
    fun cosineSimilarityMediumTest() {
        val query = listOf(TfIdfByWord(15.0, "ciel"), TfIdfByWord(13.0, "bleu"), TfIdfByWord(5.0, "chien"))
        val document = listOf(TfIdfByWord(12.0, "ciel"), TfIdfByWord(16.0, "bleu"), TfIdfByWord(8.0, "chien"))
        assert(Calculus.cosineSimilarity(query, document) - 428 / 5.196152 < 0.000001)
    }
}