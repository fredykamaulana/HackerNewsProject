package com.fmyapp.core

import android.app.Application
import com.fmyapp.core.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("Unused")
class HackerNewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HackerNewsApp)
            modules(modules)
        }
    }
}