package com.snatap.myway.ui.screens.main.breakthrough

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PrizesChildDetailsAdapter
import kotlinx.android.synthetic.main.item_prizes_detail_screen.*


class PrizesDetailsScreen : BaseFragment(R.layout.item_prizes_detail_screen) {
companion object {
    private var txtTitle: String? = null
    fun newInstance(txtTitle: String): PrizesDetailsScreen {
        Companion.txtTitle = txtTitle
        return PrizesDetailsScreen()
    }
}
    override fun initialize() {
      //  setClicks()
        initViews()
    }

    private fun initViews() {
        recyclerChamps.adapter = PrizesChildDetailsAdapter {
            //addFragment(PrizesDetailsScreen())
        }.apply {
            setData(arrayListOf(1,2,3))
        }

    }

    private fun setClicks() {
       // containerChamps.setOnClickListener { addFragment(BreakthroughScreen()) }


    }
}






