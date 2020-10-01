package com.snatap.myway.ui.screens.main.me.myway

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*

class MyWayLearnScreen : BaseFragment(R.layout.screen_myway_learn) {
    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): MyWayScreen {
            Companion.txtTitle = txtTitle
            return MyWayScreen()
        }
    }
    override fun initialize() {
        title.text = "Обучение"
    }
}
