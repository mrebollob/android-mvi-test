package com.mrebollob.mvi.presentation.posts

import androidx.lifecycle.ViewModel
import com.mrebollob.mvi.platform.mvibase.MviViewModel
import com.mrebollob.mvi.domain.extension.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val actionProcessorHolder: PostsProcessorHolder
) : ViewModel(), MviViewModel<PostsIntent, PostsViewState> {

    private val intentsSubject: PublishSubject<PostsIntent> = PublishSubject.create()
    private val statesObservable: Observable<PostsViewState> = compose()

    private val intentFilter: ObservableTransformer<PostsIntent, PostsIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(PostsIntent.LoadPostsIntent::class.java).take(1),
                    shared.notOfType(PostsIntent.LoadPostsIntent::class.java)
                )
            }
        }

    override fun processIntents(intents: Observable<PostsIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<PostsViewState> = statesObservable

    private fun compose(): Observable<PostsViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(PostsViewState.idle(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: PostsIntent): PostsAction {
        return when (intent) {
            is PostsIntent.LoadPostsIntent -> PostsAction.LoadAllPostsAction
        }
    }

    companion object {
        private val reducer = BiFunction { previousState: PostsViewState, result: PostsResult ->
            when (result) {
                is PostsResult.LoadAllPostsResult -> when (result) {
                    is PostsResult.LoadAllPostsResult.Success -> {
                        previousState.copy(isLoading = false, posts = result.posts)
                    }
                    is PostsResult.LoadAllPostsResult.Failure -> previousState.copy(
                        isLoading = false,
                        error = result.error
                    )
                    is PostsResult.LoadAllPostsResult.Loading -> previousState.copy(isLoading = true)
                }
            }
        }
    }
}