package com.snatap.myway.ui.screens

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.BlankScreen
import com.snatap.myway.ui.screens.main.EventsScreen
import com.snatap.myway.ui.screens.main.HomeScreen
import kotlinx.android.synthetic.main.screen_bottom_nav.*

class BottomNavScreen : BaseFragment(R.layout.screen_bottom_nav) {

    private var bottomFragments = arrayListOf<Fragment>(
        HomeScreen(),
        BlankScreen(),
        BlankScreen(),
        BlankScreen(),
        EventsScreen()
    )

    override fun initialize() {

        setHasOptionsMenu(true)

        bottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.home -> {
                    selectFragment(0)
                    true
                }
                R.id.path -> {
                    selectFragment(1)
                    true
                }
                R.id.me -> {
                    selectFragment(2)
                    true
                }
                R.id.breakthrough -> {
                    selectFragment(3)
                    true
                }
                R.id.events -> {
                    selectFragment(4)
                    true
                }
                else -> false
            }
        }

        selectFragment(0)

    }

    private fun selectFragment(pos: Int) {
        replaceFragment(bottomFragments[pos])
    }

    override fun observe() {
    }
}
