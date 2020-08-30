package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.NewsAdapter
import com.snatap.myway.ui.adapters.NewsRoundedAdapter
import com.snatap.myway.ui.screens.main.events.FilterBottomSheet
import com.snatap.myway.ui.screens.main.events.FilterType
import com.snatap.myway.ui.screens.main.filter.FilterDatesScreen
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.ui.screens.main.home.story.StoriesFragment
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.blockClickable
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsRoundedAdapter: NewsRoundedAdapter

    override fun initialize() {

        filter.setOnClickListener {
            it.blockClickable()
            val bottomSheet = FilterBottomSheet.newInstance(false).apply {
                setListener {
                    if (it == FilterType.TAGS) addFragment(FilterTagsScreen())
                    if (it == FilterType.DATES) addFragment(FilterDatesScreen())
                }
            }
            bottomSheet.show(childFragmentManager, "")
        }
        favorites.setOnClickListener {
            addFragment(FavoriteNewsScreen())
        }

        val data = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, "")
        newsRoundedAdapter = NewsRoundedAdapter {
            addFragment(StoriesFragment())
        }.apply { setData(data) }

        newsAdapter = NewsAdapter({
            addFragment(NewsDetailScreen.newInstance(it.id),tag = Constants.NEWS_DETAILED_FRAGMENT)
        }, { like, id ->
            if (like) viewModel.addLike(id)
            else viewModel.addBookmark(id)
        })

        recycler.adapter = newsRoundedAdapter
        recyclerNews.adapter = newsAdapter

        swipeLayout.setOnRefreshListener {
            viewModel.getNews()
        }
    }

    override fun observe() {
        viewModel.news.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            val count = ArrayList(it.filter { it.is_bookmarked }).size
            favCount.text = if (count != 0) count.toString() else ""
            newsAdapter.setData(it)
        })
    }

}

