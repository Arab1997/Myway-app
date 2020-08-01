package com.snatap.myway.ui.activities

import android.view.KeyEvent
import com.snatap.myway.R
import com.snatap.myway.base.BaseActivity
import com.snatap.myway.base.BaseViewModel
import com.snatap.myway.base.initialFragment
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.splash.SplashScreen
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.preferences.SharedManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    val viewModel by viewModel<BaseViewModel>()
    val sharedManager: SharedManager by inject()

    override fun onActivityCreated() {
        viewModel.apply {
            parentLayoutId = R.id.fragmentContainer
            navLayoutId = R.id.navContainer

            fetchData()
        }

        sharedManager.code = "4444" // todo
        sharedManager.userId = 36726 // todo

        debug()
//        startFragment()
    }

    private fun debug() = initialFragment(BottomNavScreen())

    private fun startFragment() {
        initialFragment(
            if (sharedManager.token.isEmpty()) SplashScreen()
            else BottomNavScreen(), true
        )
    }

    fun showProgress(show: Boolean) {
        progressBar.showGone(show)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> return false
                KeyEvent.KEYCODE_VOLUME_DOWN -> return false
            }
        }
        return super.dispatchKeyEvent(event)
    }
}
