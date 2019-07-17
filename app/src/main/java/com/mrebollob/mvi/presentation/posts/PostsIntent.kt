package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.platform.mvibase.MviIntent

sealed class PostsIntent : MviIntent {
    object LoadPostsIntent : PostsIntent()
    class GetAuthorInfoIntent(val id: Int) : PostsIntent()
}