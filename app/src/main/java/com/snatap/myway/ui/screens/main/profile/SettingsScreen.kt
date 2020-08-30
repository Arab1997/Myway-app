package com.snatap.myway.ui.screens.main.profile

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_settings.*

class SettingsScreen : BaseFragment(R.layout.screen_settings) {

    override fun initialize() {

        title.text = "Настройки"

        back.setOnClickListener { finishFragment() }

        profile.setOnClickListener { inDevelopment(requireContext()) }

        syncContacts.setOnClickListener { inDevelopment(requireContext()) }

        notifications.setOnClickListener { inDevelopment(requireContext()) }

        referral.setOnClickListener { inDevelopment(requireContext()) }

        history.setOnClickListener { inDevelopment(requireContext()) }

        logout.setOnClickListener { inDevelopment(requireContext()) }

        faq.setOnClickListener { inDevelopment(requireContext()) }

        about.setOnClickListener { inDevelopment(requireContext()) }
    }
}