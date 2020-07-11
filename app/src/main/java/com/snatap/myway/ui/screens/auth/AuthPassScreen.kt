package com.snatap.myway.ui.screens.auth

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enable
import com.snatap.myway.utils.views.MyWayEditText
import kotlinx.android.synthetic.main.screen_registration_pass.*

class AuthPassScreen : BaseFragment(R.layout.screen_registration_pass) {

    override fun initialize() {

        next.disable()

        pass.onTextChanged { validate(it, pass, passConfirm) }
        passConfirm.onTextChanged { validate(it, passConfirm, pass) }

        next.setOnClickListener { addFragment(AuthPinScreen()) }
    }

    private fun validate(text: String, view1: MyWayEditText, view2: MyWayEditText) {

        if (text.isEmpty() || text.length < 6) {
            view1.setError("Min length 6")
            return
        }

        if (text != view2.getText()) {
            view1.setError("Passwords doesn't match")
            view2.setError("Passwords doesn't match")
            return
        }

        view1.showHideError(false)
        view2.showHideError(false)
        next.enable()
    }
}