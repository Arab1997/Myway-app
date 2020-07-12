package com.snatap.myway.ui.screens.splash

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.base.parentLayoutId
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.auth.login.AuthLoginScreen
import com.snatap.myway.ui.screens.auth.register.AuthPhoneScreen
import kotlinx.android.synthetic.main.screen_choose_auth.*

class ChooseAuthScreen : BaseFragment(R.layout.screen_choose_auth) {

    override fun initialize() {

        continueView.setOnClickListener {
            replaceFragment(
                BottomNavScreen(),
                id = parentLayoutId()
            )
        }

        register.setOnClickListener { addFragment(AuthPhoneScreen()) }

        haveAccount.setOnClickListener { addFragment(AuthLoginScreen()) }
    }
}