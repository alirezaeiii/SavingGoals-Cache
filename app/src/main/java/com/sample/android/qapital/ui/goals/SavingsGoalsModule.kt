package com.sample.android.qapital.ui.goals

import androidx.lifecycle.ViewModelProvider
import com.sample.android.qapital.di.BaseModule
import com.sample.android.qapital.viewmodels.SavingsGoalsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SavingsGoalsModule : BaseModule() {

    @ContributesAndroidInjector
    internal abstract fun savingsGoalsFragment(): SavingsGoalsFragment

    @Binds
    internal abstract fun bindViewModelFactory(factory: SavingsGoalsViewModel.SavingsGoalsViewModelFactory): ViewModelProvider.NewInstanceFactory
}