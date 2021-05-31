package com.sample.android.qapital.network

import com.sample.android.qapital.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber
import javax.inject.Singleton

interface QapitalService {

    @GET("/savingsgoals")
    fun requestSavingGoals(): Observable<SavingsGoalWrapper>

    @GET("/savingsgoals/{id}/feed")
    fun requestFeeds(@Path("id") id: Int): Observable<FeedWrapper>

    @GET("/savingsrules")
    fun requestSavingRules(): Observable<SavingsRuleWrapper>
}

private fun getLoggerInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor {
        Timber.d(it)
    }
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

/**
 * Main entry point for network access.
 */
@Module
class Network {

    // Configure retrofit to parse JSON and use rxJava
    @Singleton
    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.QAPITAL_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(getLoggerInterceptor())
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .build()

    @Provides
    @Singleton
    fun qapitalApi(retrofit: Retrofit): QapitalService =
        retrofit.create(QapitalService::class.java)
}