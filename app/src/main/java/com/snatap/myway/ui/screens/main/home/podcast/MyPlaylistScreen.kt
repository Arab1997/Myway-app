package com.snatap.myway.ui.screens.main.home.podcast

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PodcastAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class MyPlaylistScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var adapter: PodcastAdapter
    override fun initialize() {

        title.text = "Мой плейлист"

        back.setOnClickListener { finishFragment() }

        swipeLayout.setOnRefreshListener {
            viewModel.getPlaylists()
        }

        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PodcastAdapter {
            addFragment(PodcastInfoScreen.newInstance(it))
        }
        recycler.adapter = adapter
    }

    override fun observe() {
        viewModel.getPlaylistTags()

        viewModel.playlists.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            adapter.setData(ArrayList(it.filter { it.is_bookmarked }))
        })

        viewModel.tags.observe(viewLifecycleOwner, Observer {
            adapter.setIcons(it)
        })
    }
}
