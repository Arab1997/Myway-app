package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import kotlinx.android.synthetic.main.bottomsheet_filter.*

enum class FilterType { TAGS, DATES, CITIES, FILTER }

class FilterBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_filter) {

    private lateinit var listener: (FilterType) -> Unit
    fun setListener(listener: (FilterType) -> Unit) {
        this.listener = listener
    }

    override fun initialize() {

        tags.setOnClickListener { invoke(FilterType.TAGS) }
        dates.setOnClickListener { invoke(FilterType.DATES) }
        cities.setOnClickListener { invoke(FilterType.CITIES) }
        filter.setOnClickListener { invoke(FilterType.FILTER) }
    }

    private fun invoke(type: FilterType) {
        listener.invoke(type)
        dismiss()
    }
}
