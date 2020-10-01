package com.snatap.myway.ui.screens.main.me.myday

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MyActiveAdapter
import com.snatap.myway.ui.adapters.ProgressActiveAdapter
import com.snatap.myway.ui.screens.main.me.myway.MyWayLearnScreen
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*
import kotlinx.android.synthetic.main.screen_myactive_progress.*
import kotlinx.android.synthetic.main.screen_myevent.*
import kotlinx.android.synthetic.main.screen_myevent.recyclerLearn


class MyActiveProgressScreen : BaseFragment(R.layout.screen_myactive_progress) {
    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): MyActiveProgressScreen {
            Companion.txtTitle = txtTitle
            return MyActiveProgressScreen()
        }
    }
    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {
        title.text = "Подробнее"

        recyclerProgress.adapter = ProgressActiveAdapter {
        }.apply {
            setData(arrayListOf(1))
        }
    }

    private fun setClicks() {
        close.setOnClickListener { finishFragment() }
    }
}


