package com.sample.android.qapital.di

import com.sample.android.qapital.util.CurrencyFormatter
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideCurrencyFormatter() = CurrencyFormatter(Locale.getDefault())
}