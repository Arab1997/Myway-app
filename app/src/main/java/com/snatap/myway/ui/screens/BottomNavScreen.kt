package com.snatap.myway.ui.screens

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.BlankScreen
import kotlinx.android.synthetic.main.screen_bottom_nav.*

class BottomNavScreen : BaseFragment(R.layout.screen_bottom_nav) {

    private var bottomFragments = arrayListOf<Fragment>(
        BlankScreen(),
        BlankScreen(),
        BlankScreen(),
        BlankScreen(),
        BlankScreen()
    )

    override fun initialize() {

        setHasOptionsMenu(true)

        bottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.general -> {
                    selectFragment(0)
                    true
                }
                R.id.orders -> {
                    selectFragment(1)
                    true
                }
                R.id.new_order -> {
                    selectFragment(2)
                    true
                }
                R.id.help -> {
                    selectFragment(3)
                    true
                }
                R.id.profile -> {
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
