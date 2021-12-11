package com.wakuei.githubuserlistpresent

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@MyApplication)
            val list = listOf(myModule, repoModule)
            modules(list)
        }
    }
}