package com.snatap.myway.ui.screens.auth.register

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.ActivationBottomSheet
import com.snatap.myway.ui.screens.auth.AgreementScreen
import com.snatap.myway.ui.screens.auth.login.AuthLoginScreen
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enableDisable
import kotlinx.android.synthetic.main.screen_registration_phone.*

class AuthPhoneScreen : BaseFragment(R.layout.screen_registration_phone) {

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