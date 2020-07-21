package com.snatap.myway.ui.screens.main.path

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.EventsFragment
import com.snatap.myway.ui.screens.main.EventsPagerAdapter
import com.snatap.myway.ui.screens.main.PastEventsFragment
import kotlinx.android.synthetic.main.fragment_past_path.*
import kotlinx.android.synthetic.main.screen_events.*

class PathScreen: BaseFragment(R.layout.screen_path){
    override fun initialize() {
        pager.adapter =
            PathsPagerAdapter(arrayListOf(1, 2, 3, 4), childFragmentManager)
    }

}

class PathFragment: BaseFragment(R.layout.fragment_path){
    override fun initialize() {
    }

}

class PastPathFragment: BaseFragment(R.layout.fragment_past_path){
    override fun initialize() {
        participate.setOnClickListener { addFragment(PathTasksScreen()) }
    }

}

class PathsPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return if (position == 0) PathFragment()
        else PastPathFragment()
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