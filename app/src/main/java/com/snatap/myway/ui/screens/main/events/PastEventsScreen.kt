package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Event
import com.snatap.myway.ui.adapters.PastEventsAdapter
import com.snatap.myway.utils.extensions.blockClickable
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class PastEventsScreen : BaseFragment(R.layout.screen_recycler) {

    companion object {
        private var data = arrayListOf<Event>()
        fun newInstance(data: ArrayList<Event>): PastEventsScreen {
            this.data = data
            return PastEventsScreen()
        }
    }

    override fun initialize() {

        title.text = "Прошедшие события"

        back.setOnClickListener { finishFragment() }

        right.apply {
            setImageResource(R.drawable.ic_filter_white)
            setOnClickListener {
                it.blockClickable()
                /*val bottomSheet = FilterBottomSheet.newInstance(false).apply {
                    setListener {
                        if (it == FilterType.TAGS) addFragment(FilterTagsScreen())
                        if (it == FilterType.DATES) addFragment(FilterDatesScreen())
                    }
                }
                bottomSheet.show(childFragmentManager, "")*/ // todo
            }
        }

        recycler.adapter = PastEventsAdapter {
            addFragment(EventDetailsScreen.newInstance(it))
        }.apply { setData(data) }

        swipeLayout.isEnabled = false
    }
}