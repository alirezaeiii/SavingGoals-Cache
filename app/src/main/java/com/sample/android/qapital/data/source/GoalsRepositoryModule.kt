package com.sample.android.qapital.data.source

import android.app.Application
import androidx.room.Room
import com.sample.android.qapital.data.source.local.QapitalDatabase
import com.sample.android.qapital.data.source.local.QapitalLocalDataSource
import com.sample.android.qapital.data.source.remote.SavingsGoalsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class GoalsRepositoryModule {

    @Singleton
    @Binds
    @Local
    internal abstract fun provideGoalsLocalDataSource(dataSource: QapitalLocalDataSource): GoalsDataSource

    @Singleton
    @Binds
    @Remote
    internal abstract fun provideGoalsRemoteDataSource(dataSource: SavingsGoalsRemoteDataSource): GoalsDataSource

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideDb(context: Application) =
            Room.databaseBuilder(context.applicationContext, QapitalDatabase::class.java, "Goals.db")
                .build()

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideGoalsDao(db: QapitalDatabase) = db.goalDao()
    }
}