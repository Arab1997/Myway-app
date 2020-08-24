package com.snatap.myway.ui.screens.main.home.media

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MediaContentAdapter
import kotlinx.android.synthetic.main.fragment_media_content.*

class MediaContentFragment : BaseFragment(R.layout.fragment_media_content) {

    private lateinit var adapter: MediaContentAdapter

    override fun initialize() {
        adapter = MediaContentAdapter {
            addFragment(MediaPlayerScreen.newInstance(it.video!!, false, it.title))
        }
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            viewModel.getLessonsDay()
        }
    }

    override fun observe() {
        viewModel.lessonsDay.observe(viewLifecycleOwner, Observer {
            swipeLayout.isRefreshing = false
            adapter.setData(it)
        })
    }
}