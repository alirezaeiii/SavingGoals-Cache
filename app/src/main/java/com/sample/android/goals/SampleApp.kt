package com.sample.android.goals

import com.jakewharton.threetenabp.AndroidThreeTen
import com.sample.android.goals.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class SampleApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        // Set up Timber
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}