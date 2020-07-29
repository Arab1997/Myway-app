package com.snatap.myway.utils.views.pincode

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import com.snatap.myway.R
import kotlinx.android.synthetic.main.view_pin_code.view.*

class PinCodeView : LinearLayout {
    private var code = ""

    private var constCodeLength = 4
    private lateinit var mListener: OnPinCodeListener

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_pin_code, this)
    }

    private fun pin(pos: Int): ImageView = codeViews.getChildAt(pos) as ImageView

    fun input(number: String): Int {
        if (code.length == constCodeLength) {
            return code.length
        }
        pin(code.length).setImageResource(R.drawable.ic_checked_dot)

        code += number

        if (code.length == constCodeLength) mListener.onCodeCompleted(code)
        else mListener.onCodeNotCompleted(code)

        return code.length
    }

    fun delete(): Int {
        mListener.onCodeNotCompleted(code)

        if (code.isEmpty()) return code.length

        code = code.substring(0, code.length - 1)
        pin(code.length).setImageResource(R.drawable.ic_not_checked_dot)

        return code.length
    }

    fun clearCode() {
        mListener.onCodeNotCompleted(code)

        code = ""

        codeViews.children.forEach {
            (it as ImageView).setImageResource(R.drawable.ic_not_checked_dot)
        }
    }

    fun getCodeLength() = code.length

    fun setListener(listener: OnPinCodeListener) {
        mListener = listener
    }

    interface OnPinCodeListener {
        fun onCodeCompleted(code: String)
        fun onCodeNotCompleted(code: String)
    }
}