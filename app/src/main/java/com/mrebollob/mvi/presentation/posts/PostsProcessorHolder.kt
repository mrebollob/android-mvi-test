package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.domain.repository.PostRepository
import com.mrebollob.mvi.platform.schedulers.BaseSchedulerProvider
import io.reactivex.ObservableTransformer

class PostsProcessorHolder(
    private val postRepository: PostRepository,
    private val schedulerProvider: BaseSchedulerProvider
) {

    private val loadAllPostsProcessor =
        ObservableTransformer<PostsAction.LoadAllPostsAction, PostsResult.LoadAllPostsResult> { actions ->
            actions.flatMap {
                postRepository.getAllPots()
                    .map { posts -> PostsResult.LoadAllPostsResult.Success(posts) }
                    .cast(PostsResult.LoadAllPostsResult::class.java)
                    .onErrorReturn(PostsResult.LoadAllPostsResult::Failure)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .startWith(PostsResult.LoadAllPostsResult.Loading)
            }
        }

    // TODO Check this!!!
    internal var actionProcessor = loadAllPostsProcessor as ObservableTransformer<PostsAction, PostsResult>
}