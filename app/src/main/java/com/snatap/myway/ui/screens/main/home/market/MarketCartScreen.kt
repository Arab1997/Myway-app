package com.snatap.myway.ui.screens.main.home.market

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MarketCartAdapter
import kotlinx.android.synthetic.main.screen_market_cart.*

class MarketCartScreen : BaseFragment(R.layout.screen_market_cart){

    override fun initialize() {
        recyclerItems.adapter = MarketCartAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8))
        }
    }
}