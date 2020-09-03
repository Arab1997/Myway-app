package com.snatap.myway.ui.screens.main.breakthrough

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PrizesChildAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.fragment_prizes_screen.*


class PrizesScreen : BaseFragment(R.layout.fragment_prizes_screen) {

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): PrizesScreen {
            this.txtTitle = txtTitle
            return PrizesScreen()
        }
    }

    override fun initialize() {
        setClicks()

        initViews()
    }

    private fun initViews() {
        recyclerPrizes.adapter = PrizesChildAdapter {
            addFragment(PrizesDetailsScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }
        title.text = "Чемпионы"
    }

    private fun setClicks() {
        containerPrizes.setOnClickListener { addFragment(PrizesDetailsScreen()) }
    }
}






