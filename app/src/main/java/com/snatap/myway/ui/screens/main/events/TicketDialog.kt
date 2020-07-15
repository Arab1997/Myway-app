package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.dialog_ticket.*

class TicketDialog : BaseFragment(R.layout.dialog_ticket) {

    override fun initialize() {

        root.setOnClickListener { finishFragment() }
        code.setOnClickListener { finishFragment() }
    }
}