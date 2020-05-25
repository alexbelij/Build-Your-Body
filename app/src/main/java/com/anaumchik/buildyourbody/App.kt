package com.anaumchik.buildyourbody

import android.app.Application
import com.anaumchik.buildyourbody.data.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(koinModule))
        }
    }
}
