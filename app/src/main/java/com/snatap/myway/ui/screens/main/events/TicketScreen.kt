package com.snatap.myway.ui.screens.main.events

import android.os.Handler
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.screen_ticket.*

class TicketScreen : BaseFragment(R.layout.screen_ticket) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Мои билеты"

        info.setOnClickListener { inDevelopment(requireContext()) }

        qrCodeLayout.setOnClickListener {
            addFragment(TicketDialog())
            Handler().postDelayed({ activation?.visible() }, 1000)
        }
    }
}