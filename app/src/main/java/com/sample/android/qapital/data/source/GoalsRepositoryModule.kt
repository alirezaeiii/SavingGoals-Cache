package com.sample.android.qapital.data.source

import android.app.Application
import androidx.room.Room
import com.sample.android.qapital.data.source.local.LocalDataSource
import com.sample.android.qapital.data.source.local.QapitalDatabase
import com.sample.android.qapital.data.source.local.QapitalLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class GoalsRepositoryModule {

    @Singleton
    @Binds
    internal abstract fun provideGoalsLocalDataSource(dataSource: QapitalLocalDataSource): LocalDataSource

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideDb(context: Application) =
            Room.databaseBuilder(context.applicationContext,
                QapitalDatabase::class.java,
                "Goals.db").build()

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideGoalsDao(db: QapitalDatabase) = db.goalDao()
    }
}