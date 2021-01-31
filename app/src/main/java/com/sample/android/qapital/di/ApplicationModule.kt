package com.sample.android.qapital.di

import android.app.Application
import android.content.Context
import com.sample.android.qapital.util.CurrencyFormatter
import com.sample.android.qapital.util.CurrencyFormatterDefault
import com.sample.android.qapital.util.NumberFormatter
import com.sample.android.qapital.util.schedulers.BaseSchedulerProvider
import com.sample.android.qapital.util.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Binds
    internal abstract fun bindContext(application: Application): Context

    @Binds
    internal abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideCurrencyFormatter() = CurrencyFormatter(Locale.getDefault())

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideCurrencyFormatterDefault() =
            CurrencyFormatterDefault(Locale.getDefault())

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideNumberFormatter() = NumberFormatter()
    }
}

