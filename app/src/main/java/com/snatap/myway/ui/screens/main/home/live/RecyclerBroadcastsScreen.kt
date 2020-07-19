package com.snatap.myway.ui.screens.main.home.live

import android.os.Bundle
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.AnnouncementAdapter
import com.snatap.myway.ui.adapters.BroadcastFilterItemAdapter
import com.snatap.myway.ui.adapters.RepeatAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.title
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.screen_recycler_broadcasts.*

class RecyclerBroadcastsScreen : BaseFragment(R.layout.screen_recycler_broadcasts) {

    companion object {

        fun newInstance(isRepeatsScreen: Boolean): RecyclerBroadcastsScreen {
            return RecyclerBroadcastsScreen().apply {
                arguments = Bundle().apply {
                    putBoolean("repeatsScreen", isRepeatsScreen)
                }
            }
        }
    }

    override fun initialize() {


        initSimilarViews()
        initAnnouncementViews()
        initRepeatsViews()
    }

    private fun initSimilarViews() {
        left.setOnClickListener { finishFragment() }

        recyclerFilters.adapter = BroadcastFilterItemAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        }
    }

    private fun initAnnouncementViews() {
        title.text = "Анонсы"

        recyclerBroadcasts.adapter = AnnouncementAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        }
    }

    private fun initRepeatsViews() {
        val bundle = arguments ?: return
        title.text = "Повторы"

        recyclerBroadcasts.adapter = RepeatAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        }
    }

}