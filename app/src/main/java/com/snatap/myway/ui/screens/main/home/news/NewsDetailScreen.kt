package com.snatap.myway.ui.screens.main.home.news

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.CommentAdapter
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.screen_news_detailed.*

class NewsDetailScreen : BaseFragment(R.layout.screen_news_detailed) {

    override fun initialize() {

        setClicks()

        initRecycler()
    }

    private lateinit var commentAdapter: CommentAdapter

    private fun initRecycler() {

        val data = arrayListOf(1, 2, 3, 4, 5, 6, 7, "")
        commentAdapter = CommentAdapter {
            addFragment(CommentsScreen())
        }.apply { setData(data) }

        recyclerComments.adapter = commentAdapter
    }

    private fun setClicks() {

        back.setOnClickListener { finishFragment() }

    }
}
