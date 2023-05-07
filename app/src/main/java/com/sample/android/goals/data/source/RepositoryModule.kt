package com.sample.android.goals.data.source

import android.app.Application
import androidx.room.Room
import com.sample.android.goals.data.SavingsGoalWrapper
import com.sample.android.goals.data.source.local.GoalsDatabase
import com.sample.android.goals.data.source.local.LocalDataSource
import com.sample.android.goals.data.source.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun provideGoalsLocalDataSource(dataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Singleton
    @Binds
    internal abstract fun bindGoalsRepository(repository: GoalsRepository): BaseRepository<SavingsGoalWrapper>

    @Singleton
    @Binds
    internal abstract fun bindDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository

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