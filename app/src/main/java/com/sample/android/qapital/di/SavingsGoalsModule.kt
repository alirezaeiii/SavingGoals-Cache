package com.sample.android.qapital.di

import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.ui.SavingsGoalsFragment
import com.sample.android.qapital.viewmodels.SavingsGoalsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SavingsGoalsModule {

    @ContributesAndroidInjector
    internal abstract fun savingsGoalsFragment(): SavingsGoalsFragment

    @Binds
    internal abstract fun bindViewModelFactory(factory: SavingsGoalsViewModel.Factory): ViewModelProvider.Factory
}