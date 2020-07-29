package com.snatap.myway.ui.screens.auth

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.screen_agreement.*

class AgreementScreen : BaseFragment(R.layout.screen_agreement) {

    companion object {
        private var isLicenceAgr = true
        fun newInstance(isLicenceAgr: Boolean): AgreementScreen {
            this.isLicenceAgr = isLicenceAgr
            return AgreementScreen()
        }
    }

    override fun initialize() {
        if (isLicenceAgr) {
            title.text = "Лицензионное соглашение"
            desc.text = getString(R.string.licence_aggr)
        } else {
            title.text = "Пользовательское соглашение"
            desc.text = getString(R.string.user_aggr)
        }

        back.setOnClickListener { finishFragment() }
    }

}