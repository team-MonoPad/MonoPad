package com.project.monopad

import android.app.Application
import com.project.monopad.di.monoDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MonoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(monoDiModule)
        }
    }
}