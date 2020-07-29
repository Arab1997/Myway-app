package com.snatap.myway.ui.screens.auth.login

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.ActivationBottomSheet
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthRecoverPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        next.apply {
            disable()
            text = "Продолжить"

            sendRequest()

            setOnClickListener {
                showBottomSheet()
            }
        }

        ccp.registerCarrierNumberEditText(phone)
        ccp.setPhoneNumberValidityChangeListener {
            next.enableDisable(it)
        }

        title.text = "Восстановление пароля"

        pass.gone()
        terms.invisible()
        recoverPass.invisible()
        haveAccount.gone()
    }

    private fun sendRequest() {
        next.enable()
        ccp.fullNumberWithPlus
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