package com.snatap.myway.ui.screens.auth

import androidx.fragment.app.commit
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.base.parentLayoutId
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.extensions.toast
import com.snatap.myway.utils.views.pincode.PinCodeFragment
import kotlinx.android.synthetic.main.screen_registration_pin.*

class AuthPinScreen : BaseFragment(R.layout.screen_registration_pin) {

    companion object {
        private var isRegister = true
        fun newInstance(isRegister: Boolean): AuthPinScreen {
            this.isRegister = isRegister
            return AuthPinScreen()
        }
    }

    override fun initialize() {

        title.showGone(!isRegister)

        val fragment = PinCodeFragment.newInstance(isRegister).apply {

            setRegisterListener(object : PinCodeFragment.LockCodeListener {
                override fun onCodeCreated() {
                    openBottomNavScreen()
                }

                override fun onNewCodeValidationFailed() {
                    showError("Пароли не совпадают")
                }

            })

            setLoginListener(object : PinCodeFragment.LockLoginListener {
                override fun onPinLoginFailed() {
                    showError("Пароль не правильно")
                }

                override fun onCodeInputSuccessful() {
                    openBottomNavScreen()
                }

            })
        }

        childFragmentManager.commit(allowStateLoss = true) {
            add(R.id.pinContainer, fragment)
        }
    }

    private fun openBottomNavScreen() = replaceFragment(BottomNavScreen(), id = parentLayoutId())

    private fun showError(msg: String) = toast(requireContext(), msg)

    override fun onDestroyView() {
        isRegister = true
        super.onDestroyView()
    }
}