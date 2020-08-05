package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.CommentAdapter
import com.snatap.myway.utils.common.TextWatcherInterface
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.content_add_comments.*
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_comments.*

class CommentsScreen : BaseFragment(R.layout.screen_comments) {

    companion object {
        private var newsId = 0
        fun newInstance(newsId: Int): CommentsScreen {
            this.newsId = newsId
            return CommentsScreen()
        }
    }

    private lateinit var adapter: CommentAdapter

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Комментарии"

        adapter = CommentAdapter(sharedManager)

        recycler.adapter = adapter

        addComment.addTextChangedListener(object : TextWatcherInterface {
            override fun textChanged(s: String) {
                send.showGone(s.isNotEmpty())
            }

        })

        send.setOnClickListener {
            viewModel.addComment(newsId, addComment.text.toString())
            addComment.text.clear()
        }
        userImg.loadImage(sharedManager.user.avatar)

    }

    override fun observe() {
        viewModel.getComments(newsId)

        viewModel.comments.observe(viewLifecycleOwner, Observer {
            adapter.setData(ArrayList(it.sortedByDescending { it.created_at }))
            recycler.layoutManager?.scrollToPosition(0)
        })
    }
}