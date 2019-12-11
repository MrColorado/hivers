package com.epita.tfidf.utils

import com.epita.tfidf.models.TfIdfByWord
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Calculus {
    companion object {
        private fun dotProduct(v1: List<Double>, v2: List<Double>) =
                v1.zip(v2).map { it.first * it.second }.reduce { a, b -> a + b }

        private fun euclidianDst(v1: List<Double>, v2: List<Double>) : Double {
            return sqrt(v1.zip(v2).map { (it.first - it.second).pow(2) }.sum())
        }

        fun normalize(vector: List<TfIdfByWord>): List<TfIdfByWord> {
            val squareVectorSum = vector.map { it.tfidf * it.tfidf }.sum()
            val normalizationFactor = sqrt(squareVectorSum)
            return vector.map { TfIdfByWord(it.tfidf / normalizationFactor, it.word) }
        }

        fun cosineSimilarity(query: List<TfIdfByWord>, document: List<TfIdfByWord>) : Double {
            val dot = dotProduct(query.map { it.tfidf }, document.map { it.tfidf })
            val eucli = euclidianDst(query.map { it.tfidf }, document.map { it.tfidf })
            if (abs(eucli) < 0.0000001) return dot
            return dot / eucli
        }
    }
}