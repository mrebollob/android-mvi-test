package com.mrebollob.mvi.domain.repository

import com.mrebollob.mvi.domain.model.Author
import com.mrebollob.mvi.domain.model.Post
import io.reactivex.Observable

interface PostRepository {
    fun getAllPots(): Observable<List<Post>>
    fun getAuthorInfo(id: Int): Observable<Author>
}