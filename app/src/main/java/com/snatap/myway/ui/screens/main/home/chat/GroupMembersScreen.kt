package com.snatap.myway.ui.screens.main.home.chat

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MembersAdapter
import kotlinx.android.synthetic.main.screen_group_members.*

class GroupMembersScreen : BaseFragment(R.layout.screen_group_members){

    override fun initialize() {
        recycler.adapter = MembersAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7))
        }
    }

}