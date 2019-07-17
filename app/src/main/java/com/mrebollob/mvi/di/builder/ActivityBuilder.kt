package com.mrebollob.mvi.di.builder

import com.mrebollob.mvi.presentation.posts.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributePostsActivity(): PostsActivity
}