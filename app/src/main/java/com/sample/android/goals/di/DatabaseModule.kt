package com.sample.android.goals.di

import android.app.Application
import androidx.room.Room
import com.sample.android.goals.data.local.GoalsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DatabaseModule {

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideDb(context: Application) =
            Room.databaseBuilder(
                context.applicationContext,
                GoalsDatabase::class.java,
                "Goals.db"
            ).build()

        @Singleton
        @Provides
        @JvmStatic
        internal fun provideGoalsDao(db: GoalsDatabase) = db.goalDao()
    }
}