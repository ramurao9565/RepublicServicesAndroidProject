package com.pinninti

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DriverRouteApplication : Application() {
    override fun onCreate() {
        super.onCreate()


    }
}