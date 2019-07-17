package com.mrebollob.mvi.domain.repository

import com.mrebollob.mvi.domain.model.Post
import io.reactivex.Observable

interface PostRepository {
    fun getAllPots(): Observable<List<Post>>
}