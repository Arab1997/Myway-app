package com.snatap.myway.ui.screens.main.me.dairy

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.me.MyDayScreen
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*
import kotlinx.android.synthetic.main.screen_my_dairy.send

class MyDairyScreen : BaseFragment(R.layout.screen_my_dairy) {
    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): MyDairyScreen {
            this.txtTitle = txtTitle
            return MyDairyScreen()
        }
    }

    override fun initialize() {
        setClicks()
    }

    private fun setClicks() {

        title.text = "Дневник"

        //send.setOnClickListener { addFragment(MyDayScreen()) }
        close.setOnClickListener { finishFragment() }
    }
}






