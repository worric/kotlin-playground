package me.worric.kotlinplayground

import android.app.Application

class App : Application() {

    companion object {
        var instance: App by NotNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}