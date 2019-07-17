package com.mrebollob.mvi.posts

import com.mrebollob.mvi.domain.model.Post
import com.mrebollob.mvi.domain.repository.PostRepository
import com.mrebollob.mvi.platform.schedulers.BaseSchedulerProvider
import com.mrebollob.mvi.platform.schedulers.ImmediateSchedulerProvider
import com.mrebollob.mvi.presentation.posts.PostsIntent
import com.mrebollob.mvi.presentation.posts.PostsProcessorHolder
import com.mrebollob.mvi.presentation.posts.PostsViewModel
import com.mrebollob.mvi.presentation.posts.PostsViewState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostsViewModelTest {

    @Mock
    private lateinit var postRepository: PostRepository
    private lateinit var schedulerProvider: BaseSchedulerProvider
    private lateinit var viewModel: PostsViewModel
    private lateinit var testObserver: TestObserver<PostsViewState>
    private lateinit var posts: List<Post>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        schedulerProvider = ImmediateSchedulerProvider()
        viewModel = PostsViewModel(PostsProcessorHolder(postRepository, schedulerProvider))

        posts = listOf(
            Post(1, 1, "Title 1", "Body 1"),
            Post(2, 2, "Title 2", "Body 2"),
            Post(3, 3, "Title 3", "Body 3"),
            Post(4, 4, "Title 4", "Body 4")
        )

        testObserver = viewModel.states().test()
    }


    @Test
    fun loadAllPostsFromRepositoryAndLoadIntoView() {
        `when`(postRepository.getAllPots()).thenReturn(Observable.just(posts))

        viewModel.processIntents(Observable.just(PostsIntent.LoadPostsIntent))

        testObserver.assertValueAt(1, PostsViewState::isLoading)
        testObserver.assertValueAt(2) { postsViewState: PostsViewState -> !postsViewState.isLoading }
    }

    @Test
    fun errorLoadingPostsShowsError() {
        `when`(postRepository.getAllPots()).thenReturn(Observable.error(Exception()))

        viewModel.processIntents(Observable.just(PostsIntent.LoadPostsIntent))

        testObserver.assertValueAt(2) { state -> state.error != null }
    }
}