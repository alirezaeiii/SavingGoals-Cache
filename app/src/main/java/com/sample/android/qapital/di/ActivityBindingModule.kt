package com.sample.android.qapital.di

import com.sample.android.qapital.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [SavingsGoalsModule::class, DetailModule::class])
    internal abstract fun mainActivity(): MainActivity
}