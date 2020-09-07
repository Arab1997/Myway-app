package com.snatap.myway.ui.screens.main.breakthrough

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.ChampsAdapter
import com.snatap.myway.ui.adapters.FriendsAdapter
import com.snatap.myway.ui.adapters.PrizesAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.*
import kotlinx.android.synthetic.main.fragment_wheel_of_fortune.*
import kotlinx.android.synthetic.main.screen_break_visual.*

class VisualizationBreakthroughScreen : BaseFragment(R.layout.screen_break_visual) {

    override fun initialize() {

        initViews()
    }

    private fun initViews() {

        recyclerPrizes.adapter = PrizesAdapter {
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }

        recyclerChamps.adapter = ChampsAdapter {
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }

        recyclerFriends.adapter = FriendsAdapter {
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }
    }

}


