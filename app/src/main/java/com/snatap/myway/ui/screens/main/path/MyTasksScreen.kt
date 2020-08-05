package com.snatap.myway.ui.screens.main.path

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MyTaskAdapter
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_my_tasks.*

class MyTasksScreen : BaseFragment(R.layout.screen_my_tasks) {

    override fun initialize() {

        setClicks()

        initViews()
    }

    private fun initViews() {
        back.invisible()

        title.text = "Мои задания"

        right.setImageResource(R.drawable.ic_close)

        recycler.adapter = MyTaskAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }

    private fun setClicks() {
        right.setOnClickListener { finishFragment() }

        sendTaskBtn.setOnClickListener { addFragment(SendTaskScreen()) }
    }
}
