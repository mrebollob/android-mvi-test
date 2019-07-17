package com.mrebollob.mvi.data.api


import com.mrebollob.mvi.data.entity.PostEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApiService {

    companion object {
        private const val POSTS = "/posts"
    }

    @GET(POSTS)
    fun posts(): Observable<List<PostEntity>>
}