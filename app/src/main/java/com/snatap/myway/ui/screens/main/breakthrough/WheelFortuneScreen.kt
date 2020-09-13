package com.snatap.myway.ui.screens.main.breakthrough

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wheel_of_fortune.*

class WheelFortuneScreen : BaseFragment(R.layout.fragment_wheel_of_fortune) {

    override fun initialize() {

        initViews()
    }

    private fun initViews() {

        wheel_view.setOnClickListener {
        }
        wheel_view.titles = listOf("Упс", "+12 ", "-5", "Подарок", "Промокод", "Упс", "-5")
    }

}






