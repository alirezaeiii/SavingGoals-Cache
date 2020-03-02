package com.sample.android.qapital.di

import android.app.Application
import com.sample.android.qapital.QapitalApp
import com.sample.android.qapital.data.source.GoalsRepositoryModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(
    modules = [GoalsRepositoryModule::class,
        ApplicationModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        BaseModule::class]
)
interface AppComponent : AndroidInjector<QapitalApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
