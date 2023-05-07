package com.sample.android.goals.di

import android.app.Application
import com.sample.android.goals.SampleApp
import com.sample.android.goals.data.source.RepositoryModule
import com.sample.android.goals.network.Network

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(
    modules = [RepositoryModule::class,
        ApplicationModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        Network::class]
)
interface AppComponent : AndroidInjector<SampleApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
