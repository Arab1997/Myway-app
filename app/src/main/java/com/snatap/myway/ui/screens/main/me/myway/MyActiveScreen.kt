package com.snatap.myway.ui.screens.main.me.myway

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MyActiveAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*
import kotlinx.android.synthetic.main.screen_myevent.*

class MyActiveScreen : BaseFragment(R.layout.screen_myevent) {

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
        title.text = "Моя активность"

        recyclerLearn.adapter = MyActiveAdapter {
            addFragment(MyWayLearnScreen())
        }.apply {
            setData(arrayListOf(1))
        }

        recyclerEvent.adapter = MyActiveAdapter {
            addFragment(MyWayLearnScreen())
        }.apply {
            setData(arrayListOf(1))
        }
    }
    private fun setClicks() {

    }
}
