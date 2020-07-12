package com.snatap.myway.ui.screens.auth

import androidx.fragment.app.commit
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.base.parentLayoutId
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.utils.extensions.loge
import com.snatap.myway.utils.extensions.toast
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.screen_registration_pin.*


class AuthPinScreen : BaseFragment(R.layout.screen_registration_pin) {

    private var isRegister = true
    override fun initialize() {

        arguments?.let {
            isRegister = it.getBoolean("register", true)

        }
        if (!isRegister) {
            title.visible()
            desc.text = "Введи свой код-пароль для входа в приложение"
        }

        val registerConfig = PFFLockScreenConfiguration.Builder(requireContext())
            .setMode(PFFLockScreenConfiguration.MODE_CREATE)
            .setCodeLength(4)
            .setNewCodeValidation(true)
            .setClearCodeOnError(true)
            .build()

        val loginConfig = PFFLockScreenConfiguration.Builder(requireContext())
            .setMode(PFFLockScreenConfiguration.MODE_AUTH)
            .setCodeLength(4)
            .setClearCodeOnError(true)
            .build()

        val fragment = PFLockScreenFragment().apply {
            setConfiguration(if (isRegister) registerConfig else loginConfig)
        }

        fragment.setCodeCreateListener(object :
            PFLockScreenFragment.OnPFLockScreenCodeCreateListener {
            override fun onNewCodeValidationFailed() {
                showError("Пароли не совпадают")
            }

            override fun onCodeCreated(encodedCode: String?) {
                openBottomNavScreen()
            }
        })
        fragment.setLoginListener(object : PFLockScreenFragment.OnPFLockScreenLoginListener {
            override fun onPinLoginFailed() {
                showError("Пароли не правильно")
            }

            override fun onFingerprintSuccessful() {
                openBottomNavScreen()
            }

            override fun onFingerprintLoginFailed() {
                showError("Отпечатка не правильно")
            }

            override fun onCodeInputSuccessful() {
                openBottomNavScreen()
            }

        })
        childFragmentManager.commit(allowStateLoss = true) {
            add(R.id.pinContainer, fragment)
        }
    }

    private fun openBottomNavScreen() = replaceFragment(BottomNavScreen(), id = parentLayoutId())

    private fun showError(msg: String) = toast(requireContext(), msg)
}