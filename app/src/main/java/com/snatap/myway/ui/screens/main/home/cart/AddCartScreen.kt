package com.snatap.myway.ui.screens.main.home.cart

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.AddCartAdapter
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_add_cart.*

class AddCartScreen : BaseFragment(R.layout.screen_add_cart) {

    override fun initialize() {

        setViews()

        setClicks()
    }

    private fun setClicks() {
        right.setOnClickListener { finishFragment() }
    }

    private fun setViews() {
        back.invisible()

        right.setImageResource(R.drawable.ic_close)

        title.text = "Корзина"

        recycler.adapter = AddCartAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8))
        }
    }
}