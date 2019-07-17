package com.mrebollob.mvi.di

import android.app.Application
import com.mrebollob.mvi.MviTestApplication
import com.mrebollob.mvi.di.builder.ActivityBuilder
import com.mrebollob.mvi.di.module.ApiModule
import com.mrebollob.mvi.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ActivityBuilder::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MviTestApplication)
}