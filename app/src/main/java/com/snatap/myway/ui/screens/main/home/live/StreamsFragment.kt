package com.snatap.myway.ui.screens.main.home.live

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Stream
import com.snatap.myway.ui.adapters.LiveStreamsAdapter
import com.snatap.myway.ui.adapters.StreamsAdapter
import com.snatap.myway.utils.extensions.serverDF
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

        announcementAdapter = StreamsAdapter(true) {
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
            filterStreams(it)
            swipeLayout?.isRefreshing = false
        })
    }

    private fun filterStreams(it: ArrayList<Stream>) {
        val current = System.currentTimeMillis()
        val coming = it.filter { serverDF.parse(it.date)!!.time > current }
        val live = it.filter { serverDF.parse(it.date)!!.time < current && !it.is_ended }
        val past = it.filter { serverDF.parse(it.date)!!.time < current && it.is_ended }

        liveAdapter.setData(ArrayList(live))
        announcementAdapter.setData(ArrayList(coming))
        repeatAdapter.setData(ArrayList(past))
    }
}
