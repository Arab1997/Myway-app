package com.snatap.myway.ui.screens.main.filter

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Tag
import com.snatap.myway.network.models.TagResp
import com.snatap.myway.ui.adapters.FilterTagsAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class FilterTagsScreen : BaseFragment(R.layout.screen_recycler) {

    private var listener: (ArrayList<Tag>) -> Unit = {}
    fun setListener(listener: (ArrayList<Tag>) -> Unit) {
        this.listener = listener
    }

    private lateinit var adapter: FilterTagsAdapter
    private var tags = arrayListOf<Tag>()
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Тэги"

        adapter = FilterTagsAdapter {
            tags[it].isChecked = !tags[it].isChecked
            adapter.setData(tags)
            listener.invoke(ArrayList(tags.filter { it.isChecked }))
        }
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener { viewModel.getNewsTags() }
    }

    override fun observe() {
        viewModel.apply {
            getNewsTags()
            data.observe(viewLifecycleOwner, Observer {
                if (it is TagResp) {
                    swipeLayout.isRefreshing = false
                    tags = ArrayList(it.news_item_tags)
                    adapter.setData(tags)
                }
            })
        }
    }

}