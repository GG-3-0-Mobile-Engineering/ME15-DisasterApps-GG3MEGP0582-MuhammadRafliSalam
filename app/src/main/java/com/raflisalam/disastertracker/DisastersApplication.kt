package com.raflisalam.disastertracker

import android.app.Application
import com.raflisalam.disastertracker.common.utils.NotificationUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DisastersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createNotificationChannel(this)
    }
}