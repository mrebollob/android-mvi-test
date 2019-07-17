package com.mrebollob.mvi.domain.model

import com.mrebollob.mvi.domain.extension.empty

data class Author(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
) {

    fun isValid(): Boolean {
        return name.isNotEmpty()
    }

    companion object {
        fun empty() = Author(
            Int.empty(),
            String.empty(),
            String.empty(),
            String.empty()
        )
    }
}