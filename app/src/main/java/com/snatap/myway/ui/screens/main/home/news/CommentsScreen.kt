package com.snatap.myway.ui.screens.main.home.news

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.CommentAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_comments.*

class CommentsScreen : BaseFragment(R.layout.screen_comments) {

    override fun initialize() {

        left.apply {
            setImageResource(R.drawable.ic_arrow_back_white)
            setOnClickListener { finishFragment() }
        }

        title.text = "Комментарии"

        recycler.adapter = CommentAdapter {}.apply { setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)) }
    }
}