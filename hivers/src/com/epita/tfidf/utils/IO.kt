package com.epita.tfidf.utils

import java.io.File
import java.io.InputStream

class IO {
    companion object {
        fun synonymFromFile(path: String) : HashMap<String, String> {
            val inputStream: InputStream = File(path).inputStream()
            val dict = HashMap<String, String>()
            inputStream.bufferedReader().useLines {
                lines -> lines.forEach {
                    val tokens = it.split(' ')
                    for (i in 1..tokens.size) {
                        dict[tokens[0]] = tokens[i]
                    }
                }
            }
            return dict
        }
    }
}