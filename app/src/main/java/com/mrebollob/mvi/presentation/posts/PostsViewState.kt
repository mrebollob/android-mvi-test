package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.domain.model.Post
import com.mrebollob.mvi.platform.mvibase.MviViewState

data class PostsViewState(
    val isLoading: Boolean,
    val posts: List<Post>,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): PostsViewState = PostsViewState(
            isLoading = false,
            posts = emptyList(),
            error = null
        )
    }
}