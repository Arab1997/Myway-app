package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Comment
import com.snatap.myway.ui.adapters.CommentAdapter
import com.snatap.myway.utils.common.TextWatcherInterface
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.content_add_comments.*
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_comments.*

class CommentsScreen : BaseFragment(R.layout.screen_comments) {

    companion object {
        private var itemId = 0
        private var isEventComments: Boolean = false
        fun newInstance(itemId: Int, isEventComments: Boolean): CommentsScreen {
            this.itemId = itemId
            this.isEventComments = isEventComments
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
            if (isEventComments) viewModel.addCommentToEvent(itemId, addComment.text.toString())
            else viewModel.addCommentToNews(itemId, addComment.text.toString())
            addComment.text.clear()
        }
        userImg.loadImage(sharedManager.user.avatar)

        swipeLayout.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        if (isEventComments) viewModel.getEventsComments(itemId)
        else viewModel.getNewsComments(itemId)
    }

    override fun observe() {
        fetchData()

        viewModel.apply {
            if (isEventComments) commentEvents.observe(viewLifecycleOwner, Observer { setData(it) })
            else commentNews.observe(viewLifecycleOwner, Observer { setData(it) })
        }
    }

    private fun setData(it: ArrayList<Comment>) {
        swipeLayout?.isRefreshing = false
        adapter.setData(ArrayList(it.sortedByDescending { it.created_at }))
        recycler.layoutManager?.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        itemId = 0
        isEventComments = false
    }
}