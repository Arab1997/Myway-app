package com.snatap.myway.utils.views.pincode

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Vibrator
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.utils.extensions.*
import com.snatap.myway.utils.preferences.PreferenceHelper
import com.snatap.myway.utils.preferences.SharedManager
import com.snatap.myway.utils.views.pincode.PinCodeView.OnPinCodeListener
import kotlinx.android.synthetic.main.fragment_pin_code.*

class PinCodeFragment : Fragment(R.layout.fragment_pin_code) {

    companion object {
        private var isRegister = true
        fun newInstance(isRegister: Boolean): PinCodeFragment {
            this.isRegister = isRegister
            return PinCodeFragment()
        }
    }

    private lateinit var prefs: SharedPreferences
    private lateinit var sharedManager: SharedManager

    private lateinit var loginListener: LockLoginListener
    private lateinit var registerListener: LockCodeListener

    private var mCode = ""
    private var mCodeValidation = ""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        prefs = PreferenceHelper.defaultPrefs(requireContext())
        sharedManager = SharedManager(prefs, Gson(), requireContext())

        if (!isRegister) {
            initial.text = "Введи свой код-пароль для входа в приложение"
            leftBtn.text = getString(R.string.forgot_pf)
        }

        codeView.setListener(mCodeListener)
        confirmCodeView.setListener(repeatCodeListener)

        deleteBtn.setOnClickListener(mOnDeleteButtonClickListener)
        deleteBtn.setOnLongClickListener {
            clearAll()
            true
        }
        leftBtn.setOnClickListener {
            if (codeView.getCodeLength() != 0) clearAll()
            else if (!isRegister) inDevelopment(requireContext())
        }

        initKeyViews(view)
    }

    private fun initKeyViews(parent: View) {
        parent.findViewById<TextView>(R.id.button_0)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_1)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_2)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_3)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_4)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_5)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_6)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_7)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_8)
            .setOnClickListener(mOnKeyClickListener)
        parent.findViewById<TextView>(R.id.button_9)
            .setOnClickListener(mOnKeyClickListener)
    }

    private val mOnKeyClickListener = View.OnClickListener {
        val string = (it as TextView).text.toString()
        if (codeView.getCodeLength() < 4) {
            val codeLength = codeView.input(string)
            configureRightButton(codeLength)
        } else {
            val codeLength = confirmCodeView.input(string)
            configureRightButton(codeLength)
        }

    }

    private val mOnDeleteButtonClickListener = View.OnClickListener {
        loge(confirmCodeView.getCodeLength())
        loge(codeView.getCodeLength())
        if (confirmCodeView.getCodeLength() > 0) {
            val codeLength = confirmCodeView.delete()
            configureRightButton(codeLength)
        } else {
            val codeLength = codeView.delete()
            configureRightButton(codeLength)
        }
    }

    private fun clearAll() {
        codeView.clearCode()
        confirmCodeView.clearCode()
        configureRightButton(0)
    }

    private fun configureRightButton(codeLength: Int) {
        deleteBtn.showInvisible(codeView.getCodeLength() != 0)

        if (codeLength > 0) {
            leftBtn.text = "Сброс"
        } else if (!isRegister) {
            leftBtn.text = getString(R.string.forgot_pf)
        }
    }

    private val mCodeListener: OnPinCodeListener = object : OnPinCodeListener {
        override fun onCodeCompleted(code: String) {
            if (isRegister) {
                mCode = code
                secondary.visible()
                confirmCodeView.visible()
                return
            }

            mCode = code
            if (sharedManager.code == mCode) {
                loginListener.onCodeInputSuccessful()
            } else {
                loginListener.onPinLoginFailed()
                errorAction()
                codeView!!.clearCode()
            }
        }

        override fun onCodeNotCompleted(code: String) {
            if (isRegister) {
                secondary.invisible()
                confirmCodeView.invisible()
                return
            }
        }
    }

    private val repeatCodeListener: OnPinCodeListener = object : OnPinCodeListener {
        override fun onCodeCompleted(code: String) {
            mCodeValidation = code
            onCodeEntered()
        }

        override fun onCodeNotCompleted(code: String) {
            if (code.isEmpty()) {
                secondary.invisible()
                confirmCodeView.invisible()
            }
        }
    }


    private fun onCodeEntered() {
        if (!TextUtils.isEmpty(mCodeValidation) && mCode != mCodeValidation) {
            registerListener.onNewCodeValidationFailed()
            errorAction()
            cleanCode()
            return
        }
        mCodeValidation = ""
        sharedManager.code = mCode
        registerListener.onCodeCreated()
    }

    private fun cleanCode() {
        mCode = ""
        mCodeValidation = ""
        codeView.clearCode()
        confirmCodeView.clearCode()
    }

    private fun errorAction() {
        (requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).apply {
            vibrate(400)
        }
        val animShake = AnimationUtils.loadAnimation(context, R.anim.shake_pf)
        codeView.startAnimation(animShake)
        if (isRegister) confirmCodeView.startAnimation(animShake)
    }

    fun setLoginListener(listener: LockLoginListener) {
        loginListener = listener
    }

    fun setRegisterListener(listener: LockCodeListener) {
        registerListener = listener
    }

    interface LockCodeListener {
        fun onCodeCreated()

        fun onNewCodeValidationFailed()
    }

    interface LockLoginListener {
        fun onCodeInputSuccessful()

        fun onPinLoginFailed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRegister = true
    }
}