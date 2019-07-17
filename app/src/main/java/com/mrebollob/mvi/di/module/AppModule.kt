package com.mrebollob.mvi.di.module

import com.google.gson.Gson
import com.mrebollob.mvi.platform.schedulers.BaseSchedulerProvider
import com.mrebollob.mvi.platform.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}