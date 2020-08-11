package com.snatap.myway.ui.screens.main.path

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PathTasksScreenAdapter
import com.snatap.myway.utils.extensions.invisible
import com.snatap.myway.utils.extensions.setDrawableEnd
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_path_tasks.*

class PathTasksScreen : BaseFragment(R.layout.screen_path_tasks) {
    override fun initialize() {

        back.invisible()

        title.text = "Базовый курс"

        title.setDrawableEnd(R.drawable.ic_arrow_down_white_24)

        recycler.adapter = PathTasksScreenAdapter { data, action ->
            if (action) {
            } else addFragment(VisualizationScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }
}