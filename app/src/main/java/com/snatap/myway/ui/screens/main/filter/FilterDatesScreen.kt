package com.snatap.myway.ui.screens.main.filter

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.KeyValue
import com.snatap.myway.utils.extensions.serverDF
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_filter_dates.*
import java.util.*

class FilterDatesScreen : BaseFragment(R.layout.screen_filter_dates) {

    private var listener: (KeyValue) -> Unit = {}
    fun setListener(listener: (KeyValue) -> Unit) {
        this.listener = listener
    }

    override fun initialize() {
        back.setOnClickListener { finishFragment() }
        title.text = "Дата"

        val startDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))
        val endDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))

        startDate.add(Calendar.MONTH, -2)

        calendar_view.apply {
            showDayOfWeekTitle(true)
            setRangeDate(startDate.time, endDate.time)
        }

        calendar_view.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
            listener.invoke(KeyValue(serverDF.format(startDate), serverDF.format(endDate)))
        }

    }

}