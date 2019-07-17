package com.mrebollob.mvi.data

import com.mrebollob.mvi.data.api.PostApiService
import com.mrebollob.mvi.domain.model.Post
import com.mrebollob.mvi.domain.repository.PostRepository
import io.reactivex.Observable

class PostRepositoryImp(private val apiService: PostApiService) : PostRepository {

    override fun getAllPots(): Observable<List<Post>> {

        return apiService.posts().map {
            it.map { it.toPost() }
        }
    }
}