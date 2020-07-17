package com.snatap.myway.ui.screens.main.home.news

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.NewsAdapter
import com.snatap.myway.ui.screens.main.events.FilterBottomSheet
import com.snatap.myway.ui.screens.main.events.FilterType
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private lateinit var newsRoundedAdapter: NewsRoundedAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun initialize() {

        filter.setOnClickListener {
            val bottomSheet = FilterBottomSheet.newInstance(false).apply {
                setListener {
                    if (it == FilterType.TAGS) addFragment(FilterTagsScreen())
                }
            }
            bottomSheet.show(childFragmentManager, "")
        }
        favorites.setOnClickListener {
            addFragment(FavoriteNewsScreen())
        }

        val data = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, "")
        newsRoundedAdapter = NewsRoundedAdapter {
        }.apply { setData(data) }
        newsAdapter = NewsAdapter {
            addFragment(NewsDetailScreen())
        }.apply { setData(data) }

        recycler.adapter = newsRoundedAdapter
        recyclerNews.adapter = newsAdapter
    }

    class NewsRoundedAdapter(private val listener: (Any) -> Unit) :
        BaseAdapter(R.layout.item_news_rounded) {

        override fun bindViewHolder(holder: ViewHolder, data: Any) {

        }
    }

}

