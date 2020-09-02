package com.snatap.myway.ui.screens.main.breakthrough

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.FriendsAdapter
import kotlinx.android.synthetic.main.fragment_champs_screen.*
import kotlinx.android.synthetic.main.fragment_prizes_screen.*


class FriendsScreen : BaseFragment(R.layout.fragment_prizes_screen) {


companion object {
    private var txtTitle: String? = null
    fun newInstance(txtTitle: String): FriendsScreen {
        Companion.txtTitle = txtTitle
        return FriendsScreen()
    }
}


    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {
        recyclerChamps.adapter = FriendsAdapter {
         //   addFragment(BreakthroughScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }

    }

    private fun setClicks() {
        containerChamps.setOnClickListener { addFragment(BreakthroughScreen()) }


    }
}






