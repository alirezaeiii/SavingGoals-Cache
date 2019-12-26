package com.sample.android.qapital.di

import com.sample.android.qapital.ui.detail.DetailActivity
import com.sample.android.qapital.ui.detail.DetailModule
import com.sample.android.qapital.ui.goals.SavingsGoalsActivity
import com.sample.android.qapital.ui.goals.SavingsGoalsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [SavingsGoalsModule::class])
    internal abstract fun savingsGoalsActivity(): SavingsGoalsActivity

    @ContributesAndroidInjector(modules = [DetailModule::class])
    internal abstract fun detailActivity(): DetailActivity
}