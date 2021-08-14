package com.sample.android.goals.di

import com.sample.android.goals.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class, DetailModule::class])
    internal abstract fun mainActivity(): MainActivity
}