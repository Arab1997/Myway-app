package com.snatap.myway.ui.screens.main.home.podcast

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PodcastAdapter
import com.snatap.myway.ui.adapters.PodcastBannerAdapter
import kotlinx.android.synthetic.main.fragment_podcasts.*

class PodcastsFragment : BaseFragment(R.layout.fragment_podcasts) {

    override fun initialize() {

        initViews()
    }

    private fun initViews() {
        favorites.setOnClickListener { addFragment(PodcastsPlaylistScreen()) }

        recyclerBanner.adapter = PodcastBannerAdapter {
            addFragment(PodcastInfoScreen("Ветер перемен"))
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }

        recyclerPodcasts.adapter = PodcastAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4))
        }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }
}
