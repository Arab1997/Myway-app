package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.City
import com.snatap.myway.network.models.Event
import com.snatap.myway.network.models.Tag
import com.snatap.myway.ui.adapters.PastEventsAdapter
import com.snatap.myway.ui.screens.main.filter.FilterDatesScreen
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.formatTime4
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

    private var selectedTags = arrayListOf<Tag>()
    private var selectedCities = arrayListOf<City>()
    private var startDate: String = ""
    private var endDate: String = ""

    override fun initialize() {

        title.text = "Прошедшие события"

        back.setOnClickListener { finishFragment() }

        right.apply {
            setImageResource(R.drawable.ic_filter_white)
            setOnClickListener {
                it.blockClickable()
                val bottomSheet = FilterBottomSheet.newInstance( // todo cities
                    true,
                    if (selectedTags.isNotEmpty()) selectedTags.size.toString() else "",
                    if (startDate.isNotEmpty() && endDate.isNotEmpty()) "${startDate.formatTime4()} - ${endDate.formatTime4()}" else "",
                    ""
                ).apply {
                    setListener {
                        if (it == FilterType.TAGS) addFragment(
                            FilterTagsScreen.newInstance(false).apply {
                                setSelectedTags(selectedTags)
                                setListener {
                                    selectedTags = it
                                    getEvents()
                                }
                            })
                        if (it == FilterType.DATES) addFragment(FilterDatesScreen().apply {
                            setListener {
                                startDate = it.key
                                endDate = it.value
                                getEvents()
                            }
                        })
                    }
                }
                bottomSheet.show(childFragmentManager, "")
            }
        }

        recycler.adapter = PastEventsAdapter {
            addFragment(EventDetailsScreen.newInstance(it))
        }.apply { setData(data) }

        swipeLayout.isEnabled = false
    }

    private fun getEvents() {
        val ids = ArrayList(selectedTags.map { it.id })
        viewModel.getEvents(
            startDate.getOrNull(),
            endDate.getOrNull(),
            if (ids.isNotEmpty()) ids else null
        )
    }

    private fun String.getOrNull() = if (this.isNotEmpty()) this else null
}