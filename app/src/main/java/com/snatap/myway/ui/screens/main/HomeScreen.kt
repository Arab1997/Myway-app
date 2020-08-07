package com.snatap.myway.ui.screens.main

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.home.live.LivesFragment
import com.snatap.myway.ui.screens.main.home.media.MediaContentFragment
import com.snatap.myway.ui.screens.main.home.news.NewsFragment
import com.snatap.myway.ui.screens.main.home.podcast.PodcastsFragment
import kotlinx.android.synthetic.main.screen_home.*

class HomeScreen : BaseFragment(R.layout.screen_home) {

    private var data = arrayListOf<HomeData>()

    override fun initialize() {

        data = arrayListOf(
            HomeData(R.drawable.ic_color_wand, "Контент дня", MediaContentFragment()),
            HomeData(R.drawable.ic_today, "Новости", NewsFragment()),
            HomeData(R.drawable.ic_videocam, "Прямые эфиры", LivesFragment()),
            HomeData(R.drawable.ic_microphone, "Подкасты", PodcastsFragment())
        )

        pager.adapter = HomePagerAdapter(data, childFragmentManager)

        tabLayout.setupWithViewPager(pager)

        tabLayout.getTabAt(0)!!.setIcon(data[0].icon)
        tabLayout.getTabAt(1)!!.setIcon(data[1].icon)
        tabLayout.getTabAt(2)!!.setIcon(data[2].icon)
        tabLayout.getTabAt(3)!!.setIcon(data[3].icon)
    }
}

data class HomeData(@DrawableRes val icon: Int, val title: String, val fragment: Fragment)

data class HomePagerAdapter(private val data: ArrayList<HomeData>, val fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = data[position].fragment

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int) = data[position].title

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }

    override fun saveState(): Parcelable? = null
}
