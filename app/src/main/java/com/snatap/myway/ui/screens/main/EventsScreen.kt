package com.snatap.myway.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.City
import com.snatap.myway.network.models.Event
import com.snatap.myway.network.models.Tag
import com.snatap.myway.ui.adapters.TagsAdapter
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.common.PastFragment
import com.snatap.myway.ui.screens.main.events.EventDetailsScreen
import com.snatap.myway.ui.screens.main.events.FilterBottomSheet
import com.snatap.myway.ui.screens.main.events.FilterType
import com.snatap.myway.ui.screens.main.filter.FilterDatesScreen
import com.snatap.myway.ui.screens.main.filter.FilterTagsScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.*
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.screen_events.*

class EventsScreen : BaseFragment(R.layout.screen_events) {

    private lateinit var eventsAdapter: EventsPagerAdapter
    private var selectedTags = arrayListOf<Tag>()
    private var selectedCities = arrayListOf<City>()

    private var startDate: String = ""
    private var endDate: String = ""
    override fun initialize() {

        initClicks()

        eventsAdapter = EventsPagerAdapter(childFragmentManager)

        pager.apply {
            adapter = eventsAdapter

            pageMargin = dpToPx(mainActivity, 15)

            setAnimationEnabled(true)

            setFadeFactor(0.6f)
            currentItem = 1
        }
    }

    private fun initClicks() {
        title.text = "События"

        cart.setOnClickListener { addFragment(StoreScreen()) }
        message.setOnClickListener { addFragment(ChatScreen()) }

        filter.setOnClickListener {
            it.blockClickable()
            val bottomSheet = FilterBottomSheet.newInstance(// todo cities
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
        tickets.setOnClickListener { inDevelopment(requireContext()) } // todo
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
    override fun observe() {
        viewModel.events.observe(viewLifecycleOwner, Observer {
            eventsAdapter.setData(it)
        })
    }
}

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    companion object {
        fun newInstance(data: Event): EventsFragment {
            return EventsFragment().apply {
                arguments = Bundle().apply {
                    putString("data", Gson().toJson(data))
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initialize() {

        val data = Gson().fromJson(requireArguments().getString("data"), Event::class.java)

        data.apply {
            container.setOnClickListener { addFragment(EventDetailsScreen.newInstance(this)) }

            recyclerTags.adapter = TagsAdapter().apply { setData(ArrayList(tags)) }
            backgroundImage.loadImage(photo)
            name.text = title
            desc.text = short_description.fromHtml()
            date.text = city.title + " " + start_date.formatTime4()
        }
    }
}

class EventsPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data = arrayListOf<Event>()
    fun setData(data: ArrayList<Event>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0 && data.any { it.end_date.getTime() > System.currentTimeMillis() }) PastFragment.newInstance(true)
        else EventsFragment.newInstance(data[position])
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

