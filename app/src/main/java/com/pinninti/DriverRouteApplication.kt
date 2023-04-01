package com.pinninti

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class DriverRouteApplication : Application() {
    override fun onCreate() {
        super.onCreate()


    }
}