package com.snatap.myway.ui.screens.main.events

import android.os.Bundle
import com.snatap.myway.R
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.bottomsheet_filter.*

enum class FilterType { TAGS, DATES, CITIES, FILTER }

class FilterBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_filter) {
    companion object {

        fun newInstance(newsFilter: Boolean): FilterBottomSheet {
            return FilterBottomSheet().apply {
                arguments = Bundle().apply {
                    putBoolean("newsFilter", newsFilter)
                }
            }
        }
    }

    private lateinit var listener: (FilterType) -> Unit
    fun setListener(listener: (FilterType) -> Unit) {
        this.listener = listener
    }

    override fun initialize() {

        arguments?.let {
            val newsFilter = it.getBoolean("newsFilter")
            cities.showGone(!newsFilter)
            filter.setBackgroundResource(
                if (newsFilter) R.drawable.rounded_black_card
                else R.drawable.rounded_blue_card
            )
        }

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
