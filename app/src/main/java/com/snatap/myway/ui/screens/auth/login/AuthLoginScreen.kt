package com.snatap.myway.ui.screens.auth.login

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.Token
import com.snatap.myway.ui.screens.auth.AgreementScreen
import com.snatap.myway.ui.screens.auth.AuthPinScreen
import com.snatap.myway.ui.screens.auth.register.makeTextLink
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enableDisable
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthLoginScreen : BaseFragment(R.layout.screen_registration_phone) {

    private var isRequested = false
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        terms.apply {
            text = getString(
                R.string.terms,
                getString(R.string.licence_agreement),
                getString(R.string.user_agreement)
            )
            makeTextLink(getString(R.string.licence_agreement)) {
                addFragment(AgreementScreen.newInstance(true))
            }
            makeTextLink(getString(R.string.user_agreement)) {
                addFragment(AgreementScreen.newInstance(false))
            }
        }

        next.apply {
            disable()
            text = "Войти"

            setOnClickListener {
                hideKeyboard()
                isRequested = true
                viewModel.login(ccp.fullNumberWithPlus, pass.getText())
            }
        }

        ccp.registerCarrierNumberEditText(phone)
        ccp.setPhoneNumberValidityChangeListener {
            next.enableDisable(it)
        }

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
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is Token && isRequested) {
                addFragment(AuthPinScreen.newInstance(false))
                isRequested = false
            }

        })
    }

}