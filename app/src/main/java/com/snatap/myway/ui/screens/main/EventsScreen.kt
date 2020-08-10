package com.snatap.myway.ui.screens.main

import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.events.EventDetailsScreen
import com.snatap.myway.ui.screens.main.events.PastEventsScreen
import com.snatap.myway.ui.screens.main.home.cart.StoreScreen
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.dpToPx
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_past_events.*
import kotlinx.android.synthetic.main.screen_events.*
import kotlin.math.roundToInt

class EventsScreen : BaseFragment(R.layout.screen_events) {

    override fun initialize() {

        initClicks()

        pager.apply {
            adapter =
                EventsPagerAdapter(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9), childFragmentManager)

            pageMargin = dpToPx(mainActivity, 15)

            setAnimationEnabled(true)

            setFadeFactor(0.6f)
            currentItem = 1
        }
    }

    private fun initClicks() {

        cart.setOnClickListener { addFragment(StoreScreen()) }
        message.setOnClickListener { addFragment(ChatScreen()) }

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

class PastEventsFragment : BaseFragment(R.layout.fragment_past_events) {

    private val data = arrayListOf(
        PastEventsData("", "Как найти баланс?", "24, 23 июля\n2019"),
        PastEventsData("", "Мероприятия года", "24, 23 июля\n2019"),
        PastEventsData("", "Как найти баланс?", "24, 23 июля\n2019")
    )

    override fun initialize() {
        recycler.adapter = Adapter(data) {
            inDevelopment(requireContext())
        }
        showAll.setOnClickListener {
            addFragment(PastEventsScreen())
        }
    }

    data class PastEventsData(val img: String, val title: String, val desc: String)

    class Adapter(
        private val data: ArrayList<PastEventsData>,
        private val listener: (PastEventsData) -> Unit
    ) :
        RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            parent.inflate(R.layout.item_past_events_fragment)
        )

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.apply {
                data[holder.adapterPosition].apply {
/*
                    image.loadImage(img)
                    name.text = title
                    date.text = desc
*/

                    setOnClickListener { listener.invoke(this) }
                }
            }
        }

    }
}

class EventsPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) PastEventsFragment()
        else EventsFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }
}

