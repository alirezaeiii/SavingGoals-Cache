package com.sample.android.goals.network

import com.sample.android.goals.BuildConfig
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

interface ApiService {

    @GET("/savingsgoals")
    fun requestSavingGoals(): Observable<SavingsGoalWrapperResponse>

    @GET("/savingsgoals/{id}/feed")
    fun requestFeeds(@Path("id") id: Int): Observable<FeedWrapperResponse>

    @GET("/savingsrules")
    fun requestSavingRules(): Observable<SavingsRuleWrapperResponse>
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
        .baseUrl(BuildConfig.BASE_URL)
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
    fun GoalsApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}