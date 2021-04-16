package com.sample.android.qapital.di

import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.R
import com.sample.android.qapital.data.SavingsGoal
import com.sample.android.qapital.ui.MainActivity
import com.sample.android.qapital.ui.detail.DetailFragment
import com.sample.android.qapital.ui.detail.DetailFragmentArgs
import com.sample.android.qapital.viewmodels.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {

    @ContributesAndroidInjector
    internal abstract fun detailFragment(): DetailFragment

    @Binds
    internal abstract fun bindViewModelFactory(factory: DetailViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        internal fun provideSavingsGoal(activity: MainActivity): SavingsGoal {
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            return DetailFragmentArgs.fromBundle(fragment!!.requireArguments()).savingGoal
        }
    }
}