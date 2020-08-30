package com.snatap.myway.ui.screens.main

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.profile.SettingsScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import kotlinx.android.synthetic.main.screen_profile.*

class ProfileScreen : BaseFragment(R.layout.screen_profile) {

    override fun initialize() {

        setClicks()
    }

    private fun setClicks() {
        cart.setOnClickListener { addFragment(StoreScreen()) }

        message.setOnClickListener { addFragment(ChatScreen()) }

        settings.setOnClickListener { addFragment(SettingsScreen()) }
    }

}