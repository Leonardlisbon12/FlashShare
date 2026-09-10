package com.flashshare.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlashShareApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
