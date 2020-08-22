package com.snatap.myway.ui.screens.auth

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseViewModel
import com.snatap.myway.network.ForgotResponse
import com.snatap.myway.network.RegisterResponse
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import com.snatap.myway.utils.common.TextWatcherInterface
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.hideKeyboard
import com.snatap.myway.utils.extensions.setTextColorRes
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.bottomsheet_activation.*

class ActivationBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_activation) {

    companion object {
        private var viewModel: BaseViewModel? = null
        private var phone: String = ""
        private var isRegister: Boolean = true
        fun newInstance(
            viewModel: BaseViewModel,
            phone: String,
            isRegister: Boolean
        ): ActivationBottomSheet {
            this.viewModel = viewModel
            this.phone = phone
            this.isRegister = isRegister
            return ActivationBottomSheet()
        }
    }

    private var request = false
    private lateinit var listener: (String?) -> Unit
    fun setListener(listener: (String?) -> Unit) {
        this.listener = listener
    }

    override fun initialize() {

        initView()

        initTimer()

        observe()
    }

    private fun initView() {

        codeEdt.addTextChangedListener(object : TextWatcherInterface {
            override fun textChanged(s: String) {
                if (s.length == 4) {
                    hideKeyboard(codeEdt)
                    request = true
                    if (isRegister) viewModel?.register(phone, codeEdt.text.toString())
                    else viewModel?.forgotPassword(phone, codeEdt.text.toString())
                } else {
                    progressBar.gone()
                    desc.visible()
                    error.gone()
                }
            }
        })

        timerBtn.setOnClickListener {
            timerBtn.apply {
                text = "Повторить"
                setTextColorRes(R.color.black)
                setBackgroundResource(R.drawable.rounded_edt_card)
                isClickable = false
                if (isRegister) viewModel?.register(phone)
                else viewModel?.forgotPassword(phone)
            }

            initTimer()
        }
    }

    private var timer: CountDownTimer? = null
    private fun initTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.start()
        } else {
            timer = object : CountDownTimer(60000, 1000) {
                override fun onFinish() {
                    timerBtn.apply {
                        text = "Повторить"
                        setTextColorRes(R.color.white)
                        setBackgroundResource(R.drawable.rounded_red_card)
                        isClickable = true
                    }
                }

                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    timerBtn.text = "${millisUntilFinished / 1000} сек"
                }

            }.apply { start() }

            error.gone()
            desc.visible()
        }
    }

    private fun observe() {
        viewModel?.data?.observe(viewLifecycleOwner, Observer {
            if (request) {
                request = false
                progressBar.gone()

                if (it is RegisterResponse) {
                    if (it.access_token != null) {
                        viewModel?.sharedManager?.token = it.access_token
                        dismiss()
                        listener.invoke(null)
                    } else {
                        error.visible()
                        desc.gone()
                    }
                }
                if (it is ForgotResponse) {
                    if (it.token != null) {
                        dismiss()
                        listener.invoke(it.token)
                    } else {
                        error.visible()
                        desc.gone()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}