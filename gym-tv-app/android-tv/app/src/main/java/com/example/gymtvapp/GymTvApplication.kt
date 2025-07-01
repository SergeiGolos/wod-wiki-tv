package com.example.gymtvapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GymTvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code can go here if needed
    }
}
