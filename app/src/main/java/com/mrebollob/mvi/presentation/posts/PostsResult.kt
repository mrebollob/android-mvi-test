package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.domain.model.Author
import com.mrebollob.mvi.domain.model.Post
import com.mrebollob.mvi.platform.mvibase.MviResult

sealed class PostsResult : MviResult {
    sealed class LoadAllPostsResult : PostsResult() {
        object Loading : LoadAllPostsResult()
        data class Success(val posts: List<Post>) : LoadAllPostsResult()
        data class Failure(val error: Throwable) : LoadAllPostsResult()
    }

    sealed class GetAuthorInfoResult : PostsResult() {
        object Loading : GetAuthorInfoResult()
        data class Success(val author: Author) : GetAuthorInfoResult()
        data class Failure(val error: Throwable) : GetAuthorInfoResult()
    }
}