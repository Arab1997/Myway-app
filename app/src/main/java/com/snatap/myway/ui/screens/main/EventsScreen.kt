package com.snatap.myway.ui.screens.main

import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.events.EventDetailsScreen
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.screen_events.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class EventsScreen : BaseFragment(R.layout.screen_events) {

    override fun initialize() {

        initClicks()

        title.text = "События"

        pager.setMargin()
        pager.adapter =
            EventsPagerAdapter(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9), childFragmentManager)

    }

    private fun initClicks() {

        right.setOnClickListener { inDevelopment(requireContext()) }
        rightExtra.setOnClickListener { inDevelopment(requireContext()) }

        filter.setOnClickListener { inDevelopment(requireContext()) }
        tickets.setOnClickListener { inDevelopment(requireContext()) }
    }

    private fun ViewPager.setMargin() {
        val displayMetrics = mainActivity.resources.displayMetrics
        val margin = (10 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
        this.pageMargin = margin
    }
}

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    override fun initialize() {
        container.setOnClickListener { addFragment(EventDetailsScreen()) }
    }

}

class EventsPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int) = EventsFragment().apply {
        arguments = Bundle().apply {
        }
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null
}
