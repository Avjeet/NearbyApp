package com.express.nearbyappassgn

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: App? = null

        @Synchronized
        fun getInstance(): App? {
            return instance
        }
    }
}