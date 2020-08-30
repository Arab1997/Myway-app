package com.snatap.myway.ui.screens.main.profile

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.screen_invite.*

class InviteScreen : BaseFragment(R.layout.screen_invite) {

    override fun initialize() {
        windowAdjustResize()

        back.setOnClickListener { finishFragment() }
    }
}