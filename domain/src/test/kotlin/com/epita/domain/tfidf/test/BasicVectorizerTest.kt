package com.epita.domain.tfidf.test

import com.epita.models.tfidf.Tokenized
import com.epita.models.tfidf.Vectorized
import com.epita.domain.tfidf.vectorizer.impl.BasicVectorizer
import org.junit.Test

class BasicVectorizerTest {
    private fun check(vectorized: Vectorized, name: String, frequency: Double, vararg positions: Int) {
        assert(vectorized.keywords.containsKey(name))
        assert(vectorized.keywords[name]!!.frequency == frequency)
        assert(vectorized.keywords[name]!!.positions.containsAll(positions.toList()))
    }
    @Test
    fun `test basic vectorizer use case`() {
        val vectorizer = BasicVectorizer()
        val tokenized = Tokenized("test.url", arrayListOf("foo", "bar", "foo", "fo", "ba", "bar", "bazz", "buzz"))
        val vectorized = vectorizer.compute(tokenized)
        assert(vectorized.keywords.size == 6)
        check(vectorized, "foo", 0.25, 0, 2)
        check(vectorized, "bar", 0.25, 1, 5)
        check(vectorized, "fo", 0.125, 3)
        check(vectorized, "ba", 0.125, 4)
        check(vectorized, "bazz", 0.125, 6)
        check(vectorized, "buzz", 0.125, 7)
    }
}