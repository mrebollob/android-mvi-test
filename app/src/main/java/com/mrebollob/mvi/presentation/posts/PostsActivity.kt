package com.mrebollob.mvi.presentation.posts

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrebollob.mvi.R
import com.mrebollob.mvi.domain.extension.gone
import com.mrebollob.mvi.domain.extension.viewModel
import com.mrebollob.mvi.domain.extension.visible
import com.mrebollob.mvi.platform.BaseActivity
import com.mrebollob.mvi.platform.mvibase.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*

class PostsActivity : BaseActivity(), MviView<PostsIntent, PostsViewState> {


    private val adapter = PostAdapter(mutableListOf())
    private val disposables = CompositeDisposable()

    private lateinit var postsViewModel: PostsViewModel

    override fun layoutId(): Int = R.layout.activity_posts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postsViewModel = viewModel(viewModelFactory) {}

        setSupportActionBar(toolbar)

        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        bind()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun bind() {
        disposables.add(postsViewModel.states().subscribe(this::render))
        postsViewModel.processIntents(intents())
    }

    override fun intents(): Observable<PostsIntent> {
        return loadIntent()
    }

    override fun render(state: PostsViewState) {

        if (state.isLoading) {
            progressBar.visible()
        } else {
            progressBar.gone()

            if (state.posts.isEmpty()) {
                postsRecyclerView.gone()
                emptyState.visible()
            } else {
                postsRecyclerView.visible()
                emptyState.gone()
                adapter.updatePosts(state.posts)
            }
        }

        if (state.error != null) {
            Toast.makeText(this, getString(R.string.error_loading_posts), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadIntent(): Observable<PostsIntent> {
        return Observable.just(PostsIntent.LoadPostsIntent)
    }
}
