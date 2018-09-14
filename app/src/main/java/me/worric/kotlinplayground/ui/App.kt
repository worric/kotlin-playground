package me.worric.kotlinplayground.ui

import android.app.Application
import me.worric.kotlinplayground.extensions.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}