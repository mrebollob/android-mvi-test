package com.mrebollob.mvi.presentation.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrebollob.mvi.R
import com.mrebollob.mvi.domain.extension.inflate
import com.mrebollob.mvi.domain.model.Post
import kotlinx.android.synthetic.main.list_item_post.view.*

class PostAdapter(private val posts: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var onPostClicked: (Post) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_post))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position], onPostClicked)
    }

    override fun getItemCount() = posts.size

    fun updatePosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    fun setOnPostClicked(onPostClicked: (Post) -> Unit) {
        this.onPostClicked = onPostClicked
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var post: Post

        fun bind(post: Post, onPostClicked: (Post) -> Unit) {
            this.post = post
            itemView.titleTextView.text = post.title
            itemView.bodyTextView.text = post.body

            itemView.setOnClickListener { onPostClicked(post) }
        }
    }
}