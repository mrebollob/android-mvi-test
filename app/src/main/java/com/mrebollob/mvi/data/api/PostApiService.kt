package com.mrebollob.mvi.data.api


import com.mrebollob.mvi.data.entity.AuthorEntity
import com.mrebollob.mvi.data.entity.PostEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApiService {

    companion object {
        private const val POSTS = "/posts"
        private const val USERS = "/users"
    }

    @GET(POSTS)
    fun posts(): Observable<List<PostEntity>>

    @GET(USERS)
    fun users(): Observable<List<AuthorEntity>>
}