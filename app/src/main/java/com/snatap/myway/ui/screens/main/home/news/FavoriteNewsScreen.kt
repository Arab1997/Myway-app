package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.NewsAdapter
import com.snatap.myway.utils.Constants
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class FavoriteNewsScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var newsAdapter: NewsAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Моя подборка"

        newsAdapter = NewsAdapter({
            addFragment(NewsDetailScreen.newInstance(it.id), tag = Constants.NEWS_DETAILED_FRAGMENT)
        }, { like, id ->
            if (like) viewModel.likeNews(id)
            else viewModel.bookmarkNews(id)
        })

        recycler.adapter = newsAdapter

        swipeLayout.isEnabled = false
    }

    override fun observe() {
        viewModel.news.observe(viewLifecycleOwner, Observer {
            val filtered = ArrayList(it.filter { it.is_bookmarked })
            newsAdapter.setData(filtered)
        })
    }

}