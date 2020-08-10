package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PastEventsAdapter
import com.snatap.myway.ui.screens.main.filter.FilterDatesScreen
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class PastEventsScreen : BaseFragment(R.layout.screen_recycler) {

    override fun initialize() {

        title.text = "Прошедшие события"

        back.setOnClickListener { finishFragment() }

        right.apply {
            setImageResource(R.drawable.ic_filter_white)
            setOnClickListener {
                it.blockClickable()
                val bottomSheet = FilterBottomSheet.newInstance(false).apply {
                    setListener {
                        if (it == FilterType.TAGS) addFragment(FilterTagsScreen())
                        if (it == FilterType.DATES) addFragment(FilterDatesScreen())
                    }
                }
                bottomSheet.show(childFragmentManager, "")
            }
        }

        recycler.adapter = PastEventsAdapter {
            inDevelopment(requireContext())
        }.apply { setData(arrayListOf(1, 2, 3, 4, 5, 6)) }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }
}