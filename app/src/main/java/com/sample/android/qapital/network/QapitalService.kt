package com.sample.android.qapital.network

import com.sample.android.qapital.BuildConfig
import com.sample.android.qapital.data.Feed
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.data.SavingsRule
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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

    class SavingsGoalWrapper(
        @Json(name = "savingsGoals")
        val wrapper: List<SavingsGoal>
    )

    class FeedWrapper(
        @Json(name = "feed")
        val wrapper: List<Feed>
    )

    class SavingsRuleWrapper(
        @Json(name = "savingsRules")
        val wrapper: List<SavingsRule>
    )
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

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
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .build()

    @Provides
    @Singleton
    fun qapitalApi(retrofit: Retrofit): QapitalService =
        retrofit.create(QapitalService::class.java)
}