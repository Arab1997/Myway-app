package com.snatap.myway.ui.screens.main.home.cart

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.CartAdapter
import com.snatap.myway.ui.adapters.CartTagsAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_cart.*

class CartScreen : BaseFragment(R.layout.screen_cart) {

    override fun initialize() {

        initViews()

        setClicks()
    }

    private fun setClicks() {
        right.setOnClickListener { addFragment(AddCartScreen()) }
    }

    private fun initViews() {

        title.text = "Маркет MyWay"
        right.setImageResource(R.drawable.ic_cart)

        recyclerMenu.adapter = CartTagsAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6))
        }
        recyclerItems.adapter = CartAdapter {
            addFragment(ProductDetailsScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13))
        }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }

}