package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.platform.mvibase.MviAction

sealed class PostsAction : MviAction {
    object LoadAllPostsAction : PostsAction()
    data class GetAuthorInfoAction(val id: Int) : PostsAction()
}