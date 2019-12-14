package com.epita.models

class Constants {
    companion object {
        val urls: ArrayList<String> = arrayListOf("https://en.wikipedia.org/wiki/Main_Page",
            "https://medium.com/")
        const val serverPort: Int = 7000
        const val indexPort: Int = 7001
        const val serverUrl: String = "http://localhost:$serverPort/"
        const val maxBodySize: Long = 16777216 // 4096 * 4096
    }
}