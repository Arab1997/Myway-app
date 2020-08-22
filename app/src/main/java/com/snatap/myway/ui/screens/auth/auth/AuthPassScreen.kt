package com.snatap.myway.ui.screens.auth.auth

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.base.parentLayoutId
import com.snatap.myway.network.ForgotResponse
import com.snatap.myway.network.Token
import com.snatap.myway.network.models.SuccessResp
import com.snatap.myway.ui.screens.BottomNavScreen
import com.snatap.myway.ui.screens.auth.AuthPinScreen
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.disable
import com.snatap.myway.utils.extensions.enable
import com.snatap.myway.utils.views.MyWayEditText
import kotlinx.android.synthetic.main.screen_registration_pass.*

class AuthPassScreen : BaseFragment(R.layout.screen_registration_pass) {

    companion object {
        private var isRegister = true
        private var token: String? = null
        private var phone: String? = null
        fun newInstance(
            isRegister: Boolean,
            token: String? = null,
            phone: String? = null
        ): AuthPassScreen {
            this.token = token
            this.phone = phone
            this.isRegister = isRegister
            return AuthPassScreen()
        }
    }

    private var request = false
    override fun initialize() {

        initCommonView()

        if (!isRegister) initRecoverView()
    }

    private fun initCommonView() {

        next.disable()

        pass.onTextChanged { validate(it, pass, passConfirm) }
        passConfirm.onTextChanged { validate(it, passConfirm, pass) }

        next.setOnClickListener {
            it.blockClickable()
            request = true
            showProgress(true)
            if (isRegister) viewModel.setPassword(pass.getText())
            else viewModel.forgotPassword(phone!!, null, token!!, pass.getText())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initRecoverView() {
        title.text = "С возвращением! \uD83E\uDD17"
        next.text = "Готово"
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

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (request && it is SuccessResp) {
                request = false
                showProgress(false)
                replaceFragment(AuthPinScreen.newInstance(true))
            }
            if (request && it is ForgotResponse) {
                request = false
                showProgress(false)
                viewModel.login(phone!!, pass.getText())
                showProgress(true)
            }

            if (it is Token) {
                showProgress(false)
                replaceFragment(BottomNavScreen(), id = parentLayoutId())
            }

        })
    }
}