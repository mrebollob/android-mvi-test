package com.mrebollob.mvi.data.entity

import com.mrebollob.mvi.domain.model.Author

data class AuthorEntity(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val email: String = ""
) {

    fun toAuthor(): Author = with(this) {
        Author(
            id = id,
            name = name,
            username = username,
            email = email
        )
    }
}