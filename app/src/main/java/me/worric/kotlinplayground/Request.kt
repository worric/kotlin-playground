package me.worric.kotlinplayground

import android.util.Log
import java.net.URL

class Request(val url: String) {

    fun run() {
        val forecastJsonString = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonString)
    }

}