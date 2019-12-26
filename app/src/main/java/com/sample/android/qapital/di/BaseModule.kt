package com.sample.android.qapital.di

import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.util.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class BaseModule {

    @Binds
    internal abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}