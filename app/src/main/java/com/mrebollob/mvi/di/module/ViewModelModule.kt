package com.mrebollob.mvi.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrebollob.mvi.di.PostsViewModelFactory
import com.mrebollob.mvi.di.annotation.ViewModelKey
import com.mrebollob.mvi.presentation.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(postsViewModel: PostsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PostsViewModelFactory): ViewModelProvider.Factory
}