package com.snatap.myway.ui.screens.auth

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
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
                validate()
            }
        })

        pass.onTextChanged {
            validate()
        }


        title.text = "Авторизация"
        terms.text = "Восстановить пароль"
        terms.setOnClickListener { inDevelopment(requireContext()) }

        pass.visible()
        haveAccount.gone()
    }

    private fun validate() {
        if (pass.getText().length <= 6) pass.setError("Min length is 6")

        pass.showHideError(false)

        if (pass.getText().isNotEmpty() && phone.rawText.length >= 10) next.enable()
    }

    private fun sendRequest() {
        next.enable()

        // todo
        hideKeyboard()
//        addFragment()
    }

}