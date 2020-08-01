package com.snatap.myway.ui.screens.main.filter

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.screen_filter_dates.*
import java.util.*

class FilterDatesScreen : BaseFragment(R.layout.screen_filter_dates) {

    override fun initialize() {
        val startDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))
        val endDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))
        startDate.add(Calendar.MONTH, -2)

        calendar_view.apply {
            showDayOfWeekTitle(true)
            setRangeDate(startDate.time, endDate.time)
        }

        calendar_view.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
        }

    }

}