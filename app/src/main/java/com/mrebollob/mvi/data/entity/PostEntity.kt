package com.mrebollob.mvi.data.entity

import com.mrebollob.mvi.domain.model.Post

data class PostEntity(
    val id: Int = 0,
    val userId: Int = 0,
    val title: String = "",
    val body: String = ""
) {

    fun toPost(): Post = with(this) {
        Post(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}