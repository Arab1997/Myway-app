package com.snatap.myway.ui.screens.main.breakthrough


import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.*
import kotlinx.android.synthetic.main.fragment_all_friends.*

class WheelFortuneAllFriendsScreen : BaseFragment(R.layout.fragment_all_friends) {
    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {

        recyclerAllFriend.adapter = WheelAllFriendsAdapter {
          //  addFragment(WheelFortuneScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }
    }

    private fun setClicks() {
        // inviteFriend.setOnClickListener { addFragment(ChampsScreen()) }
    }
}


