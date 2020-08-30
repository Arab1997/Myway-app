package com.snatap.myway.ui.screens.main.profile

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*

class NotificationsScreen : BaseFragment(R.layout.screen_notifications) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Уведомления"

    }
}