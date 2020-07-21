package com.snatap.myway.ui.screens.main.home.news

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.NewsAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class FavoriteNewsScreen : BaseFragment(R.layout.screen_recycler) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Моя подборка"

        recycler.adapter = NewsAdapter {}.apply { setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) }
    }

}