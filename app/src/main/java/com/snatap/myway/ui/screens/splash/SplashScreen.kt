package com.snatap.myway.ui.screens.splash

import android.os.Handler
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment

class SplashScreen : BaseFragment(R.layout.screen_splash) {

    private lateinit var listener: () -> Unit
    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun initialize() {
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed({ listener.invoke() }, splashScreenDuration)
    }

    private fun getSplashScreenDuration() = 3000L

}
