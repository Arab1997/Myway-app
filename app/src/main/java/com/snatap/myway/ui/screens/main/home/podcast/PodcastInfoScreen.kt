package com.snatap.myway.ui.screens.main.home.podcast

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PodcastChapterAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.screen_podcast_info.*

class PodcastInfoScreen(title: String): BaseFragment(R.layout.screen_podcast_info) {

    val titleTxt = title

    override fun initialize() {

        initViews()
    }

    private fun initViews() {
        title.text = titleTxt

        back.setOnClickListener { finishFragment() }

        info.text = ""
        info.setBackgroundResource(R.drawable.ic_mark_white)

        recyclerChapters.adapter = PodcastChapterAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8,9,10))
        }
    }

}