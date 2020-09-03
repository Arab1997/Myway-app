package com.snatap.myway.ui.screens.main.filter

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.EventsTagResp
import com.snatap.myway.network.models.NewsTagResp
import com.snatap.myway.network.models.Tag
import com.snatap.myway.ui.adapters.FilterTagsAdapter
import com.snatap.myway.utils.Constants
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*
import kotlin.random.Random

class FilterTagsScreen : BaseFragment(R.layout.screen_recycler) {

    companion object {
        private var isEventTags = false
        fun newInstance(isEventTags: Boolean): FilterTagsScreen {
            this.isEventTags = isEventTags
            return FilterTagsScreen()
        }
    }

    private var listener: (ArrayList<Tag>) -> Unit = {}
    fun setListener(listener: (ArrayList<Tag>) -> Unit) {
        this.listener = listener
    }

    private var selectedTags = arrayListOf<Tag>()
    fun setSelectedTags(tags: ArrayList<Tag>) {
        selectedTags = tags
    }

    private var tags = arrayListOf<Tag>()
    private lateinit var adapter: FilterTagsAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Тэги"

        adapter = FilterTagsAdapter {
            tags[it].isChecked = !tags[it].isChecked
            adapter.setData(tags)
            listener.invoke(ArrayList(tags.filter { it.isChecked }))
        }
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener { fetchData() }
    }

    private fun fetchData() {
        if (isEventTags) viewModel.getEventsTags()
        else viewModel.getNewsTags()
    }

    override fun observe() {
        viewModel.apply {

            fetchData()

            data.observe(viewLifecycleOwner, Observer {
                if (it is NewsTagResp) {
                    tags = ArrayList(it.news_item_tags)
                    setData()
                }
                if (it is EventsTagResp) {
                    tags = ArrayList(it.event_tags)
                    setData()
                }
            })
        }
    }

    private fun setData() {
        swipeLayout.isRefreshing = false

        selectedTags.forEach { selected ->
            tags.forEach { if (it.id == selected.id) it.isChecked = true }
        }
        tags.forEach {
            it.color = Constants.colors[Random.nextInt(0, 100) % Constants.colors.size]
        }
        adapter.setData(tags)
    }
}