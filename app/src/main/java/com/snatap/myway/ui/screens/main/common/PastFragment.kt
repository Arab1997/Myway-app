package com.snatap.myway.ui.screens.main.common

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Event
import com.snatap.myway.ui.adapters.Past
import com.snatap.myway.ui.adapters.PastEventsFragmentAdapter
import com.snatap.myway.ui.screens.main.events.EventDetailsScreen
import com.snatap.myway.ui.screens.main.events.PastEventsScreen
import com.snatap.myway.utils.extensions.getTime
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.fragment_past.*

class PastFragment : BaseFragment(R.layout.fragment_past) {

    companion object {
        private var isEvents: Boolean = false
        fun newInstance(isEvents: Boolean): PastFragment {
            this.isEvents = isEvents
            return PastFragment()
        }
    }

    private lateinit var adapter: PastEventsFragmentAdapter
    override fun initialize() {

        adapter = PastEventsFragmentAdapter { past ->
            if (isEvents) {
                addFragment(EventDetailsScreen.newInstance(eventsList.first { it.id == past.id }))
            } else inDevelopment(requireContext()) // todo
        }
        recycler.adapter = adapter
        showAll.setOnClickListener {
            if (isEvents) addFragment(PastEventsScreen.newInstance(eventsList))
            else inDevelopment(requireContext()) // todo
        }
    }

    private var eventsList = arrayListOf<Event>()
    override fun observe() {

        viewModel.apply {
            if (isEvents) events.observe(viewLifecycleOwner, Observer {
                eventsList =
                    ArrayList(it.filter { it.end_date.getTime() > System.currentTimeMillis() })
                val list = ArrayList(
                    eventsList.map { Past(it.id, it.photo, it.title, it.start_date) }
                )
                adapter.setData(list)
            })
            else {
                // todo
            }
        }
    }
}
