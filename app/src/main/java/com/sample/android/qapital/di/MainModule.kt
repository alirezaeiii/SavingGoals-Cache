package com.sample.android.qapital.di

import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.ui.MainFragment
import com.sample.android.qapital.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun savingsGoalsFragment(): MainFragment

    @Binds
    internal abstract fun bindViewModelFactory(factory: MainViewModel.Factory): ViewModelProvider.Factory
}