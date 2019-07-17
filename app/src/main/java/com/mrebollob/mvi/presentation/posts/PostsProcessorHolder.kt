package com.mrebollob.mvi.presentation.posts

import com.mrebollob.mvi.domain.repository.PostRepository
import com.mrebollob.mvi.platform.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
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

    private val getAuthorInfoProcessor =
        ObservableTransformer<PostsAction.GetAuthorInfoAction, PostsResult.GetAuthorInfoResult> { actions ->
            actions.flatMap {
                postRepository.getAuthorInfo(it.id)
                    .map { author -> PostsResult.GetAuthorInfoResult.Success(author) }
                    .cast(PostsResult.GetAuthorInfoResult::class.java)
                    .onErrorReturn(PostsResult.GetAuthorInfoResult::Failure)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .startWith(PostsResult.GetAuthorInfoResult.Loading)
            }
        }

    internal var actionProcessor =
        ObservableTransformer<PostsAction, PostsResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(PostsAction.LoadAllPostsAction::class.java).compose(loadAllPostsProcessor),
                    shared.ofType(PostsAction.GetAuthorInfoAction::class.java).compose(getAuthorInfoProcessor)
                )
                    .mergeWith(
                        // Error for not implemented actions
                        shared.filter { v ->
                            v !is PostsAction.LoadAllPostsAction
                                    && v !is PostsAction.GetAuthorInfoAction
                        }.flatMap { w ->
                            Observable.error<PostsResult>(
                                IllegalArgumentException("Unknown Action type: $w")
                            )
                        }
                    )
            }
        }
}