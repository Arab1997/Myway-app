package com.snatap.myway.ui.screens.main.home.podcast

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.AudioPlaylistResp
import com.snatap.myway.ui.adapters.PodcastAdapter
import com.snatap.myway.ui.adapters.PodcastBannerAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_filter.*
import kotlinx.android.synthetic.main.fragment_podcasts.*

class PodcastsFragment : BaseFragment(R.layout.fragment_podcasts) {

    private lateinit var adapter: PodcastAdapter
    private lateinit var bannerAdapter: PodcastBannerAdapter

    override fun initialize() {

        initViews()
    }

    private fun initViews() {
        favorites.text = "Мой плейлист"
        favorites.setOnClickListener { addFragment(MyPlaylistScreen()) }

        filter.setOnClickListener { inDevelopment(requireContext()) } // todo


        adapter = PodcastAdapter {
            addFragment(PodcastInfoScreen.newInstance(it))
        }
        recyclerPodcasts.adapter = adapter

        swipeLayout.setOnRefreshListener {
            viewModel.getPlaylists()
        }
    }

    override fun observe() {
        viewModel.getPlaylistTags()

        viewModel.playlists.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            val count = it.filter { it.is_bookmarked }.size
            favCount.text = if (count != 0) count.toString() else ""
            adapter.setData(it)

            if (it.isNotEmpty()) {
                viewModel.getPlaylist(it.first().id)
                bannerAdapter = PodcastBannerAdapter(it.first()) {
                    addFragment(PodcastInfoScreen.newInstance(it))
                }
                recyclerBanner.adapter = bannerAdapter
            }
        })

        viewModel.tags.observe(viewLifecycleOwner, Observer {
            adapter.setIcons(it)
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is AudioPlaylistResp) {
                bannerAdapter.setData(ArrayList(it.audio_playlist.audio_items!!))
            }
        })
    }
}
