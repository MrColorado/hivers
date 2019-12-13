package com.epita.domain.tfidf.utils

import java.io.InputStream

class IO {
    companion object {
        fun synonymFromFile() : HashMap<String, String> {
            val inputStream: InputStream = IO::class.java.classLoader.getResourceAsStream("synonyms.txt")!!
            val dict = HashMap<String, String>()
            inputStream.bufferedReader().useLines {
                lines -> lines.forEach {
                    val tokens = it.split(", ".toRegex())
                    for (i in 1 until tokens.size) {
                        if (' ' !in tokens[i]) {
                            dict[tokens[i]] = tokens[0]
                        }
                    }
                }
            }
            return dict
        }
    }
}