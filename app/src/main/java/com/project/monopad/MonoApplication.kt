package com.project.monopad

import android.app.Application
import com.project.monopad.di.*
import com.project.monopad.extension.setupKoin

class MonoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin(
            this,
            networkModule,
            remoteDataSourceModule,
            localDataSourceModule,
            repositoryModule,
            viewModelModule
        )
    }
}