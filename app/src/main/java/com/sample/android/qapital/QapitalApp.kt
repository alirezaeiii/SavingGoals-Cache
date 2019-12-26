package com.sample.android.qapital

import com.jakewharton.threetenabp.AndroidThreeTen
import com.sample.android.qapital.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class QapitalApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        // Set up Timber
        Timber.plant(Timber.DebugTree())

        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}