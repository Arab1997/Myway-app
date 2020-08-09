package com.snatap.myway.ui.screens.main.home.live

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.StreamsAdapter
import com.snatap.myway.ui.adapters.TagsAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_streams_category.*

class StreamsCategoryScreen : BaseFragment(R.layout.screen_streams_category) {

    companion object {
        private var isAnnounceStreams: Boolean = false
        fun newInstance(isAnnounceStreams: Boolean): StreamsCategoryScreen {
            this.isAnnounceStreams = isAnnounceStreams
            return StreamsCategoryScreen()
        }
    }

    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var streamsAdapter: StreamsAdapter

    override fun initialize() {
        back.setOnClickListener { finishFragment() }

        title.text = if (isAnnounceStreams) "Анонсы" else "Повторы"

        tagsAdapter = TagsAdapter() // todo setData

        streamsAdapter = StreamsAdapter(isAnnounceStreams) {
            addFragment(LiveStreamScreen.newInstance(true, it))
        }

        recyclerTags.adapter = tagsAdapter
        recyclerStreams.adapter = streamsAdapter

        swipeLayout.setOnRefreshListener {
            viewModel.getStreams()
        }
    }

    override fun observe() {
        viewModel.streams.observe(viewLifecycleOwner, Observer {
            streamsAdapter.setData(it)// todo filter and setData
            swipeLayout?.isRefreshing = false
        })
    }
}