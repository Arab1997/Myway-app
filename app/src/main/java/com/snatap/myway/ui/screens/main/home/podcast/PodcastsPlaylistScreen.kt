package com.snatap.myway.ui.screens.main.home.podcast

import android.view.View
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*

class PodcastsPlaylistScreen: BaseFragment(R.layout.screen_podcasts_playlist) {

    override fun initialize() {
        title.text = "Мой плейлист"
        back.setOnClickListener { finishFragment() }
        info.visibility = View.INVISIBLE


    }

}