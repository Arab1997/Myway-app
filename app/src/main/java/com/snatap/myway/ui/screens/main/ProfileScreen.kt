package com.snatap.myway.ui.screens.main

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.me.MyDayScreen
import com.snatap.myway.ui.screens.main.me.MyResultsScreen
import com.snatap.myway.ui.screens.main.me.MyWayScreen
import com.snatap.myway.ui.screens.main.profile.SettingsScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import kotlinx.android.synthetic.main.screen_profile.*
import kotlinx.android.synthetic.main.screen_profile.pager

class ProfileScreen : BaseFragment(R.layout.screen_profile) {
    private var data = arrayListOf<Data>()

    override fun initialize() {

        data = arrayListOf(
            Data("Мой день", MyDayScreen()),
            Data("Мой путь", MyWayScreen()),
            Data("Мои результаты", MyResultsScreen())
        )
        pager.adapter = PagerAdapter(data, childFragmentManager)
        tabLayoutProfile.setupWithViewPager(pager)

        tabLayoutProfile.getTabAt(0)
        tabLayoutProfile.getTabAt(1)
        tabLayoutProfile.getTabAt(2)

        setClicks()
    }

    private fun setClicks() {
        cart.setOnClickListener { addFragment(StoreScreen()) }

        message.setOnClickListener { addFragment(ChatScreen()) }

        settings.setOnClickListener { addFragment(SettingsScreen()) }
    }
}

data class Data(val title: String, val fragment: Fragment)
data class PagerAdapter(private val data: ArrayList<Data>, val fm: FragmentManager) :
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
