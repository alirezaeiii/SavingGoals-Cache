package com.sample.android.goals.di

import com.sample.android.goals.data.SavingsGoalWrapper
import com.sample.android.goals.data.BaseRepository
import com.sample.android.goals.data.DetailsRepository
import com.sample.android.goals.repository.DetailsRepositoryImpl
import com.sample.android.goals.repository.GoalsRepository
import com.sample.android.goals.data.local.LocalDataSource
import com.sample.android.goals.data.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
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
}