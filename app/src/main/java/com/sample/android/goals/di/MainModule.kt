package com.sample.android.goals.di

import androidx.lifecycle.ViewModelProvider
import com.sample.android.goals.ui.MainFragment
import com.sample.android.goals.viewmodels.MainViewModel
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