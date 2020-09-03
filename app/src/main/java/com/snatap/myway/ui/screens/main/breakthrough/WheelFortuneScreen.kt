package com.snatap.myway.ui.screens.main.breakthrough

import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.ChampsChildDetailsAdapter
import kotlinx.android.synthetic.main.fragment_wheel_of_fortune.*
import kotlinx.android.synthetic.main.item_prizes_detail_screen.*
import kotlinx.android.synthetic.main.item_prizes_detail_screen.recyclerChamps
import me.sujanpoudel.wheelview.WheelView


class WheelFortuneScreen : BaseFragment(R.layout.fragment_wheel_of_fortune) {
companion object {
    private var txtTitle: String? = null
    fun newInstance(txtTitle: String): WheelFortuneScreen {
        Companion.txtTitle = txtTitle
        return WheelFortuneScreen()
    }
}
    override fun initialize() {
      //  setClicks()
        initViews()


    }

    private fun initViews() {
      /*  recyclerAllFriend.adapter = ChampsChildDetailsAdapter {
            //addFragment(PrizesDetailsScreen())
        }.apply {
            setData(arrayListOf(1))
        }*/

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        wheel_view.setOnClickListener {
        }
        wheel_view.titles = listOf("Упс", "+12 ", "-5", "Подарок", "Промокод", "Упс", "-5")

    }

    private fun setClicks() {
       // containerChamps.setOnClickListener { addFragment(BreakthroughScreen()) }


    }
}






