package com.snatap.myway.ui.screens.main

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.breakthrough.VisualizationBreakthroughScreen
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.common.PastFragment
import com.snatap.myway.ui.screens.main.store.StoreScreen
import com.snatap.myway.utils.extensions.dpToPx
import com.snatap.myway.utils.extensions.gone
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.*
import kotlinx.android.synthetic.main.fragment_breakthrough.*
import kotlinx.android.synthetic.main.screen_events.*

class BreakthroughScreen : BaseFragment(R.layout.screen_events) {

    private lateinit var pagerAdapter: PathsPagerAdapter

    override fun initialize() {
        pagerAdapter = PathsPagerAdapter(childFragmentManager)

        filterPanel.gone()

        pager.apply {
            adapter = pagerAdapter

            pageMargin = dpToPx(mainActivity, 15)

            setAnimationEnabled(true)

            setFadeFactor(0.6f)
            currentItem = 1
        }

        title.text = "Прорыв"

        cart.setOnClickListener { addFragment(StoreScreen()) }
        message.setOnClickListener { addFragment(ChatScreen()) }

    }

    override fun observe() {
        pagerAdapter.setData(arrayListOf(1, 2, 3))
//        viewModel.events.observe(viewLifecycleOwner, Observer {
//            eventsAdapter.setData(it)
//        })
    }
}

class PathFragment : BaseFragment(R.layout.fragment_breakthrough) {

    override fun initialize() {
        participate1.setOnClickListener { addFragment(VisualizationBreakthroughScreen()) }
    }
}

class PathsPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0) PastFragment.newInstance(false) //todo
        else PathFragment()
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
