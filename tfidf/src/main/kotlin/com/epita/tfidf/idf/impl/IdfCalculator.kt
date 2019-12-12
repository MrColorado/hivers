package com.epita.tfidf.idf.impl

import java.lang.Math.log

class IdfCalculator {
    companion object {
        fun compute(corpusSize: Int, matchingSize: Int) : Double {
            return log(corpusSize.toDouble() / (1 + matchingSize).toDouble())
        }
    }
}