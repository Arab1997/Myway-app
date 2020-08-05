package com.snatap.myway.ui.screens.main.path

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_simple_toolbar.*
import kotlinx.android.synthetic.main.screen_path.pager
import kotlinx.android.synthetic.main.screen_visualization.*
import kotlinx.android.synthetic.main.screen_visualization.view.tabLayout

class VisualizationScreen: BaseFragment(R.layout.screen_visualization){

    override fun initialize() {
        title.text = "Визуализация"

        pager.adapter =
            VisualizationPagerAdapter(arrayListOf(1, 2), childFragmentManager)
        tabLayout.setupWithViewPager(pager)
    }

}

class TaskScreen: BaseFragment(R.layout.screen_task){
    override fun initialize() {
    }

}

class LessonDetailsScreen: BaseFragment(R.layout.screen_details_lesson){
    override fun initialize() {
    }

}

class VisualizationPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return if (position == 0) LessonDetailsScreen()
        else TaskScreen()
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