package com.sample.android.qapital.di

import com.sample.android.qapital.util.CurrencyFormatter
import com.sample.android.qapital.util.CurrencyFormatterFraction
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideCurrencyFormatter() = CurrencyFormatter(Locale.getDefault())

    @Provides
    @Singleton
    internal fun provideCurrencyFormatterFraction() = CurrencyFormatterFraction(Locale.getDefault())
}