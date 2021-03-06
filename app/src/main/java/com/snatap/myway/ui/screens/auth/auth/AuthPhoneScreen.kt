package com.snatap.myway.ui.screens.auth.auth

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.ForgotResponse
import com.snatap.myway.network.RegisterResponse
import com.snatap.myway.ui.screens.auth.ActivationBottomSheet
import com.snatap.myway.ui.screens.auth.AgreementScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

    companion object {
        private var isRegister = true
        fun newInstance(isRegister: Boolean): AuthPhoneScreen {
            this.isRegister = isRegister
            return AuthPhoneScreen()
        }
    }

    private var request = false
    override fun initialize() {

        initCommonView()

        if (isRegister) initRegisterView()
        else initRecoverView()

    }

    private fun initCommonView() {

        back.setOnClickListener { finishFragment() }

        next.apply {
            disable()
            setOnClickListener { sendRequest() }
        }

        ccp.registerCarrierNumberEditText(phone)

        ccp.setPhoneNumberValidityChangeListener {
            next.enableDisable(it)
        }
    }

    private fun initRegisterView() {

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

        haveAccount.setOnClickListener {
            popInclusive()
            addFragment(AuthLoginScreen())
        }

    }

    private fun initRecoverView() {
        next.text = "Продолжить"
        title.text = "Восстановление пароля"

        pass.gone()
        terms.invisible()
        recoverPass.invisible()
        haveAccount.gone()
    }

    private fun sendRequest() {
        hideKeyboard()
        showProgress(true)
        request = true
        if (isRegister) viewModel.register(ccp.fullNumberWithPlus)
        else viewModel.forgotPassword(ccp.fullNumberWithPlus)
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (request) {
                showProgress(false)
                request = false
                next.enable()

                var phone = ccp.fullNumberWithPlus
                val bottomSheet = ActivationBottomSheet
                    .newInstance(viewModel, phone, isRegister)
                    .apply {
                        setListener { token ->
                            addFragment(
                                AuthPassScreen.newInstance(isRegister, token, phone)
                            )
                        }
                    }

                if (it is RegisterResponse && it.needs_verification != null && it.needs_verification) {
                    if (!bottomSheet.isAdded) bottomSheet.show(childFragmentManager, "")
                }

                if (it is ForgotResponse) {
                    if (!bottomSheet.isAdded) bottomSheet.show(childFragmentManager, "")
                }

            }
        })
    }
}

fun TextView.makeTextLink(str: String, action: (() -> Unit)) {
    val spannableString = SpannableString(this.text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            action.invoke()
        }

        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.isUnderlineText = true
        }
    }
    val index = spannableString.indexOf(str)
    spannableString.setSpan(
        clickableSpan, index, index + str.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
}