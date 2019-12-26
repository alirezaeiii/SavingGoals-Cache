package com.sample.android.qapital.ui.detail

import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.di.BaseModule
import com.sample.android.qapital.ui.detail.DetailActivity.Companion.EXTRA_SAVINGS_GOAL
import com.sample.android.qapital.viewmodels.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule : BaseModule() {

    @ContributesAndroidInjector
    internal abstract fun detailFragment(): DetailFragment

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideGoal(activity: DetailActivity): SavingsGoal =
            activity.intent.extras.getParcelable(EXTRA_SAVINGS_GOAL)
    }

    @Binds
    internal abstract fun bindViewModelFactory(factory: DetailViewModel.DetailViewModelFactory): ViewModelProvider.NewInstanceFactory
}