package com.ozone.defendroid

import android.app.Application
import com.google.android.material.color.DynamicColors

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}