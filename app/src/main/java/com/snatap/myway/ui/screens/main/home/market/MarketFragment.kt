package com.snatap.myway.ui.screens.main.home.market

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MarketItemAdapter
import com.snatap.myway.ui.adapters.MarketMenuAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.fragment_market.*

class MarketFragment : BaseFragment(R.layout.fragment_market){

    override fun initialize() {
        initViews()

        recyclerMenu.adapter = MarketMenuAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6))
        }
        recyclerItems.adapter = MarketItemAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13))
        }
    }

    private fun initViews(){
        title.text = "Маркет MyWay"
        right.setImageResource(R.drawable.ic_cart)
    }

}