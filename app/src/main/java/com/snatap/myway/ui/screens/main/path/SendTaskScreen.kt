package com.snatap.myway.ui.screens.main.path

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_send_task.*


class SendTaskScreen : BaseFragment(R.layout.screen_send_task) {
    override fun initialize() {
        initViews()

        setClicks()
    }

    private fun initViews() {
        back.invisible()

        title.text = "Отправить задание"

        right.setImageResource(R.drawable.ic_close)
    }

    private fun setClicks() {
        right.setOnClickListener { finishFragment() }

        chooseFileBtn.setOnClickListener {
            inDevelopment(requireContext())
        }
    }

}