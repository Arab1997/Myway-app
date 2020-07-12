package com.snatap.myway.ui.screens.auth.login

import android.annotation.SuppressLint
import android.os.Bundle
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.base.parentLayoutId
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.auth.AuthPinScreen
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enable
import com.snatap.myway.utils.views.MyWayEditText
import kotlinx.android.synthetic.main.screen_registration_pass.*

class AuthRecoverPassScreen : BaseFragment(R.layout.screen_registration_pass) {

    @SuppressLint("SetTextI18n")
    override fun initialize() {

        next.disable()
        title.text = "С возвращением! \uD83E\uDD17"
        next.text = "Готово"

        pass.onTextChanged { validate(it, pass, passConfirm) }
        passConfirm.onTextChanged { validate(it, passConfirm, pass) }

        next.setOnClickListener { replaceFragment(BottomNavScreen(), id = parentLayoutId()) }
    }

    private fun validate(text: String, view1: MyWayEditText, view2: MyWayEditText) {

        if (text.isEmpty()) {
            hideKeyboard()
            view1.showHideError(false)
            view2.showHideError(false)
        }

        if (text.isEmpty() || text.length < 6) {
            view1.setError(getString(R.string.pass_min_length))
            return
        }

        if (text != view2.getText()) {
            view1.setError(getString(R.string.pass_not_match))
            view2.setError(getString(R.string.pass_not_match))
            return
        }

        view1.showHideError(false)
        view2.showHideError(false)
        next.enable()
    }
}