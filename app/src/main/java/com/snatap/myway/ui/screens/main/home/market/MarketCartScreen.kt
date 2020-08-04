package com.snatap.myway.ui.screens.main.home.market

import android.view.View
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MarketCartAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_market_cart.*

class MarketCartScreen : BaseFragment(R.layout.screen_market_cart){

    override fun initialize() {

        setViews()

        setClicks()
    }

    private fun setClicks(){
        right.setOnClickListener { finishFragment() }
    }

    private fun setViews(){
        right.setImageResource(R.drawable.ic_close)
        back.visibility = View.GONE
        title.text = "Корзина"


        recyclerItems.adapter = MarketCartAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8))
        }
    }
}