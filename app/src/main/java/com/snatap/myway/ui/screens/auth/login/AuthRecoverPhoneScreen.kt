package com.snatap.myway.ui.screens.auth.login

import android.os.Bundle
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.ActivationBottomSheet
import com.snatap.myway.ui.screens.auth.AuthPinScreen
import com.snatap.myway.ui.screens.auth.login.AuthLoginScreen
import com.snatap.myway.ui.screens.auth.register.AuthPassScreen
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.TextWatcher
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthRecoverPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        next.apply {
            disable()
            text = "Продолжить"

            setOnClickListener {
                showBottomSheet()
            }
        }

        phone.addTextChangedListener(object : TextWatcher(true) {
            override fun textChanged(s: String) {
                if (phone.rawText.length >= 10) sendRequest()
            }
        })

        title.text = "Восстановление пароля"

        pass.gone()
        terms.invisible()
        recoverPass.invisible()
        haveAccount.gone()
    }

    private fun sendRequest() {
        next.enable()
        hideKeyboard()
        // todo
    }

    private fun showBottomSheet() {
        val bottomSheet = ActivationBottomSheet()
            .apply {
                setListener { addFragment(AuthRecoverPassScreen()) }
            }
        bottomSheet.show(childFragmentManager, "")
    }
}