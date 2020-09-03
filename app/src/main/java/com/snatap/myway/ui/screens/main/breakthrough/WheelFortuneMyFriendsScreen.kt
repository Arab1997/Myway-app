package com.snatap.myway.ui.screens.main.breakthrough


import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.*
import kotlinx.android.synthetic.main.fragment_my_friends.*

class WheelFortuneMyFriendsScreen : BaseFragment(R.layout.fragment_my_friends) {
    override fun initialize() {
        // setClicks()
        initViews()
    }

    private fun initViews() {

        recyclerMyFriend.adapter = WheelMyFriendsAdapter {
            //    addFragment(WheelFortuneScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }
    }

    private fun setClicks() {
        //  container2.setOnClickListener { addFragment(ChampsScreen()) }

    }
}


