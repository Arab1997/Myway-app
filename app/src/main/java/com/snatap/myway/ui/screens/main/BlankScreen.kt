package com.snatap.myway.ui.screens.main

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.BreakAdapter
import kotlinx.android.synthetic.main.fragment_past_break.*

class BlankScreen : BaseFragment(R.layout.fragment_past_break) {

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): BlankScreen {
            this.txtTitle = txtTitle
            return BlankScreen()
        }
    }


    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {
        recycler1.adapter = BreakAdapter {
            addFragment(BreakthroughScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }

    }

    private fun setClicks() {
        container1.setOnClickListener { addFragment(BreakthroughScreen()) }

    }
}






