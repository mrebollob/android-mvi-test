package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.domain.model.Author
import com.mrebollob.mvi.domain.model.Post
import com.mrebollob.mvi.platform.mvibase.MviViewState

data class PostsViewState(
    val isLoading: Boolean,
    val posts: List<Post>,
    val selectedAuthor: Author,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): PostsViewState = PostsViewState(
            isLoading = false,
            posts = emptyList(),
            selectedAuthor = Author.empty(),
            error = null
        )
    }
}