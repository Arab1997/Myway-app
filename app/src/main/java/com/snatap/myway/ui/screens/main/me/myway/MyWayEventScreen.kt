package com.snatap.myway.ui.screens.main.me.myway

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MywayEventAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*
import kotlinx.android.synthetic.main.screen_myway_event.*

class MyWayEventScreen : BaseFragment(R.layout.screen_myway_event) {

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): MyWayScreen {
            Companion.txtTitle = txtTitle
            return MyWayScreen()
        }
    }

    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {
        title.text = "События"

        recyclerLearn.adapter = MywayEventAdapter {
            addFragment(MyWayLearnScreen())
        }.apply {
            setData(arrayListOf(1))
        }

        recyclerEvent.adapter = MywayEventAdapter {
            addFragment(MyWayLearnScreen())
        }.apply {
            setData(arrayListOf(1))
        }
    }

    private fun setClicks() {

    }
}
