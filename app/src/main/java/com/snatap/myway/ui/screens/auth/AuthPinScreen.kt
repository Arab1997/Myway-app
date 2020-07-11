package com.snatap.myway.ui.screens.auth

import androidx.fragment.app.commit
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment


class AuthPinScreen : BaseFragment(R.layout.screen_registration_pin) {

    override fun initialize() {

        val config = PFFLockScreenConfiguration.Builder(requireContext())
            .setMode(PFFLockScreenConfiguration.MODE_CREATE)
            .setCodeLength(4)
            .setUseFingerprint(true)
            .build()

        val fragment = PFLockScreenFragment().apply { setConfiguration(config) }
        fragment.setCodeCreateListener(object :
            PFLockScreenFragment.OnPFLockScreenCodeCreateListener {
            override fun onNewCodeValidationFailed() {
            }

            override fun onCodeCreated(encodedCode: String?) {
            }
        })

        childFragmentManager.commit(allowStateLoss = true) {
            add(R.id.pinContainer, fragment)
        }
    }
}