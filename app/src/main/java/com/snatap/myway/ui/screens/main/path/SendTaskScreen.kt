package com.snatap.myway.ui.screens.main.path

import android.util.TypedValue
import android.view.View
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.screen_send_task.*
import kotlin.math.roundToInt


class SendTaskScreen: BaseFragment(R.layout.screen_send_task){
    override fun initialize() {
        initViews()

        setClicks()
    }

    private fun initViews(){
        back.visibility = View.GONE
        title.text = "Мои задания"
        val img = requireContext().resources.getDrawable(R.drawable.ic_close)
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 24F, resources.displayMetrics
        ).roundToInt()
        img.setBounds(0, 0, px, px)
        info.setCompoundDrawables(img, null, null, null)
        info.text = ""
    }

    private fun setClicks(){
        info.setOnClickListener { finishFragment() }

        chooseFileBtn.setOnClickListener {
            // TODO: 7/22/20 choose file 
        }
    }

}