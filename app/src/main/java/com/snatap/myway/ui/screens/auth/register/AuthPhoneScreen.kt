package com.snatap.myway.ui.screens.auth.register

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.ActivationBottomSheet
import com.snatap.myway.ui.screens.auth.login.AuthLoginScreen
import com.snatap.myway.utils.common.TextObservable
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enable
import com.snatap.myway.utils.extensions.enableDisable
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.screen_registration_phone.*


class AuthPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        terms.setOnClickListener { inDevelopment(requireContext()) }

        next.apply {
            disable()
            setOnClickListener {
                sendRequest()
                showBottomSheet()
            }
        }

        haveAccount.setOnClickListener {
            popInclusive()
            addFragment(AuthLoginScreen())
        }

        ccp.registerCarrierNumberEditText(phone)
        ccp.setPhoneNumberValidityChangeListener {
            next.enableDisable(it)
        }
    }

    private fun sendRequest() {
        hideKeyboard()
//        ccp.fullNumberWithPlus
        // todo
    }

    private fun showBottomSheet() {
        val bottomSheet = ActivationBottomSheet()
            .apply {
                setListener { addFragment(AuthPassScreen()) }
            }
        bottomSheet.show(childFragmentManager, "")
    }
}