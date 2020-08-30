package com.snatap.myway.utils.views

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.snatap.myway.R
import com.snatap.myway.utils.common.TextWatcherInterface
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.hideKeyboard
import com.snatap.myway.utils.extensions.setTextColorRes
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.view_edit_text.view.*

class MyWayEditText : LinearLayout {

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_edit_text, this)

        context?.obtainStyledAttributes(attrs, R.styleable.MyWayEditText)?.let {
            val inputType = it.getInt(R.styleable.MyWayEditText_input_type, 1)
            val hint = it.getString(R.styleable.MyWayEditText_hint)
            val icon = it.getDrawable(R.styleable.MyWayEditText_hint)
            val clickable = it.getBoolean(R.styleable.MyWayEditText_clickable, true)

            edt.isClickable = clickable
            edt.isEnabled = clickable
            icon?.let { edt.setCompoundDrawables(null, null, it, null) }
            hint?.let { setHint(it) }

            when (inputType) {
                1 -> edt.inputType = InputType.TYPE_CLASS_TEXT
                2 -> edt.inputType = InputType.TYPE_CLASS_NUMBER
                3 -> edt.transformationMethod = PasswordTransformationMethod.getInstance()
                else -> edt.inputType = InputType.TYPE_CLASS_TEXT
            }
        }

        edt.addTextChangedListener(object : TextWatcherInterface {
            override fun textChanged(s: String) {
                if (s.isEmpty()) hideKeyboard(edt)
                action?.invoke(s)
            }
        })

    }

    fun setHint(hint: String) {
        if (hint.isEmpty()) edtHint.gone()
        else edtHint.text = hint
    }

    fun setError(error: String) {
        errorEdt.text = error
        showHideError(true)
    }

    fun showHideError(show: Boolean) {
        edtHint.setTextColorRes(if (show) R.color.red else R.color.hint)
        errorEdt.showGone(show)
    }

    fun getText() = edt.text.toString()

    private var action: ((String) -> Unit)? = null
    fun onTextChanged(action: (String) -> Unit) {
        this.action = action
    }
}