package com.snatap.myway.ui.screens.main.live

import android.widget.ImageView
import android.widget.TextView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.AnnouncementAdapter
import com.snatap.myway.ui.adapters.RepeatAdapter
import kotlinx.android.synthetic.main.screen_main_broadcast.*

class MainBroadcastScreen: BaseFragment(R.layout.screen_main_broadcast){

    override fun initialize() {
        initViews()

    }

    private fun initViews() {
        main_health_layout.apply {
            (findViewById<ImageView>(R.id.tagIcon)).setColorFilter(context.resources.getColor(R.color.white))
            (findViewById<TextView>(R.id.tagText)).setTextColor(context.resources.getColor(R.color.white))
        }

        main_business_layout.setBackgroundResource(R.drawable.rounded_blue_card)
        main_business_layout.apply {
            (findViewById<ImageView>(R.id.tagIcon)).setColorFilter(context.resources.getColor(R.color.white))
            (findViewById<ImageView>(R.id.tagIcon)).setBackgroundResource(R.drawable.ic_business)
            (findViewById<TextView>(R.id.tagText)).setTextColor(context.resources.getColor(R.color.white))
            (findViewById<TextView>(R.id.tagText)).text = "Бизнес"
        }

        recyclerAnnouncements.adapter = AnnouncementAdapter().apply {
            setData(arrayListOf(1, 2))
        }

        recyclerRepeats.adapter = RepeatAdapter().apply {
            setData(arrayListOf(1, 2))
        }

        recyclerAnnouncementsAllBtn.setOnClickListener {
            addFragment(RecyclerBroadcastsScreen())
        }

        recyclerRepeatsAllBtn.setOnClickListener {
            addFragment(RecyclerBroadcastsScreen.newInstance(true))
        }
    }

}