package com.snatap.myway.ui.screens.auth

import android.annotation.SuppressLint
import android.os.CountDownTimer
import com.snatap.myway.R
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import com.snatap.myway.utils.common.TextWatcherInterface
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.hideKeyboard
import com.snatap.myway.utils.extensions.setTextColorRes
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.bottomsheet_activation.*

class ActivationBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_activation) {

    val code = "1111"

    private lateinit var listener: () -> Unit
    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun initialize() {

        initView()

        initTimer()
    }

    private fun initView() {

        codeEdt.addTextChangedListener(object : TextWatcherInterface {
            override fun textChanged(s: String) {
                if (s.length == 4) {
                    hideKeyboard(codeEdt)

                    if (s == code) {
                        dismiss()
                        listener.invoke()
                    } else {
                        error.visible()
                        desc.gone()
                    }
//                    viewModel.sendCode(userPhone, edt_code.text.toString())
                } else {
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

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}