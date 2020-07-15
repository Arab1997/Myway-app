package com.snatap.myway.ui.screens.auth.login

import android.os.Bundle
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.AuthPinScreen
import com.snatap.myway.utils.Constants.IS_REGISTER_KEY
import com.snatap.myway.utils.common.TextWatcher
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthLoginScreen : BaseFragment(R.layout.screen_registration_phone) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        next.apply {
            disable()
            text = "Войти"

            setOnClickListener {
                sendRequest()
            }
        }

        phone.addTextChangedListener(object : TextWatcher(true) {
            override fun textChanged(s: String) {
                if (phone.rawText.length >= 10) validate()
            }
        })

        pass.onTextChanged { validate() }

        recoverPass.setOnClickListener { addFragment(AuthRecoverPhoneScreen()) }

        title.text = "Авторизация"

        recoverPass.visible()
        pass.visible()
        haveAccount.gone()
    }

    private fun validate() {
        if (pass.getText().isNotEmpty() && pass.getText().length < 6) {
            pass.setError(getString(R.string.pass_min_length))
            return
        }

        pass.showHideError(false)

        if (pass.getText().isNotEmpty() && phone.rawText.length >= 10) {
            next.enable()
        }
    }

    private fun sendRequest() {
        next.enable()

        // todo
        hideKeyboard()

        if (phone.rawText == "9999999999" && pass.getText() == "123456") {
            addFragment(AuthPinScreen().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_REGISTER_KEY, false)
                }
            })
        } else {
            toast(requireContext(), "Неверный логин и/или пароль")
        }
    }

}