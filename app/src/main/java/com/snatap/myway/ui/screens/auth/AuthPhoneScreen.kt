package com.snatap.myway.ui.screens.auth

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.TextWatcher
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enable
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        terms.setOnClickListener {
            popInclusive()
            addFragment(AuthLoginScreen())
        }

        next.apply {
            disable()
            setOnClickListener {
                showBottomSheet()
            }
        }

        haveAccount.setOnClickListener { inDevelopment(requireContext()) }

        phone.addTextChangedListener(object : TextWatcher(true) {
            override fun textChanged(s: String) {
                if (phone.rawText.length >= 10) sendRequest()
                else next.disable()
            }
        })
    }

    private fun sendRequest() {
        next.enable()

        // todo
    }

    private fun showBottomSheet() {
        val bottomSheet = ActivationBottomSheet().apply {
            setListener { addFragment(AuthPassScreen()) }
        }
        bottomSheet.show(childFragmentManager, "")
    }
}