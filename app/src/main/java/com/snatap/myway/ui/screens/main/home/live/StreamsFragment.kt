package com.snatap.myway.ui.screens.main.home.live

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.LiveStreamsAdapter
import com.snatap.myway.ui.adapters.StreamsAdapter
import kotlinx.android.synthetic.main.fragment_streams.*

class StreamsFragment : BaseFragment(R.layout.fragment_streams) {

    private lateinit var liveAdapter: LiveStreamsAdapter
    private lateinit var announcementAdapter: StreamsAdapter
    private lateinit var repeatAdapter: StreamsAdapter

    override fun initialize() {

        initViews()
    }

    private fun initViews() {

        liveAdapter = LiveStreamsAdapter {
            addFragment(LiveStreamScreen.newInstance(true, it))
        }

        announcementAdapter = StreamsAdapter(false) {
            addFragment(LiveStreamScreen.newInstance(false, it))
        }

        repeatAdapter = StreamsAdapter(false) {
            addFragment(LiveStreamScreen.newInstance(false, it))
        }

        recyclerLive.adapter = liveAdapter
        recyclerAnnouncements.adapter = announcementAdapter
        recyclerRepeats.adapter = repeatAdapter

        allAnnouncement.setOnClickListener { addFragment(StreamsCategoryScreen.newInstance(true)) }

        allRepeats.setOnClickListener { addFragment(StreamsCategoryScreen.newInstance(false)) }

        swipeLayout.setOnRefreshListener {
            viewModel.getStreams()
        }
    }

    override fun observe() {
        viewModel.streams.observe(viewLifecycleOwner, Observer {
            liveAdapter.setData(it)// todo set data
            announcementAdapter.setData(it)
            repeatAdapter.setData(it)
            swipeLayout?.isRefreshing = false
        })
    }

}