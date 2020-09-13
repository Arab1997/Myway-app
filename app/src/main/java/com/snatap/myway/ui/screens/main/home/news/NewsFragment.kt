package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Tag
import com.snatap.myway.ui.adapters.NewsAdapter
import com.snatap.myway.ui.adapters.NewsRoundedAdapter
import com.snatap.myway.ui.screens.main.events.FilterBottomSheet
import com.snatap.myway.ui.screens.main.events.FilterType
import com.snatap.myway.ui.screens.main.filter.FilterDatesScreen
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.ui.screens.main.home.story.StoriesFragment
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.formatTime4
import kotlinx.android.synthetic.main.content_filter.*
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsRoundedAdapter: NewsRoundedAdapter
    private var selectedTags = arrayListOf<Tag>()
    private var startDate: String = ""
    private var endDate: String = ""
    override fun initialize() {

        filter.setOnClickListener {
            it.blockClickable()
            val bottomSheet = FilterBottomSheet.newInstance(
                true,
                if (selectedTags.isNotEmpty()) selectedTags.size.toString() else "",
                if (startDate.isNotEmpty() && endDate.isNotEmpty()) "${startDate.formatTime4()} - ${endDate.formatTime4()}" else "",
                ""
            ).apply {
                setListener {
                    if (it == FilterType.TAGS) addFragment(
                        FilterTagsScreen.newInstance(false).apply {
                            setSelectedTags(selectedTags)
                            setListener {
                                selectedTags = it
                                getNews()
                            }
                        })
                    if (it == FilterType.DATES) addFragment(FilterDatesScreen().apply {
                        setListener {
                            startDate = it.key
                            endDate = it.value
                            getNews()
                        }
                    })
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
            addFragment(NewsDetailScreen.newInstance(it.id), tag = Constants.NEWS_DETAILED_FRAGMENT)
        }, { like, id ->
            if (like) viewModel.likeNews(id)
            else viewModel.bookmarkNews(id)
        })

        recycler.adapter = newsRoundedAdapter
        recyclerNews.adapter = newsAdapter

        swipeLayout.setOnRefreshListener {
            viewModel.getNews()
        }
    }

    private fun getNews() {
        val ids = ArrayList(selectedTags.map { it.id })
        viewModel.getNews(
            startDate.getOrNull(),
            endDate.getOrNull(),
            if (ids.isNotEmpty()) ids else null
        )
    }

    private fun String.getOrNull() = if (this.isNotEmpty()) this else null
    override fun observe() {
        viewModel.news.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            val count = ArrayList(it.filter { it.is_bookmarked }).size
            favCount.text = if (count != 0) count.toString() else ""
            newsAdapter.setData(it)
        })
    }

}

