package com.snatap.myway.ui.screens.splash

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.auth.AuthLoginScreen
import com.snatap.myway.ui.screens.auth.AuthPhoneScreen
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.screen_choose_auth.*

class ChooseAuthScreen : BaseFragment(R.layout.screen_choose_auth) {

    override fun initialize() {

        continueView.setOnClickListener { addFragment(BottomNavScreen()) }

        register.setOnClickListener { addFragment(AuthPhoneScreen()) }

        haveAccount.setOnClickListener { addFragment(AuthLoginScreen()) }
    }
}