package com.snatap.myway.ui.screens.main.home.podcast

import androidx.recyclerview.widget.GridLayoutManager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PodcastAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class PodcastsPlaylistScreen : BaseFragment(R.layout.screen_recycler) {

    override fun initialize() {
        title.text = "Мой плейлист"

        back.setOnClickListener { finishFragment() }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }

        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = PodcastAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6))
        }
    }
}