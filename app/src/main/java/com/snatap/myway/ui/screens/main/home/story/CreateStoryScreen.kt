package com.snatap.myway.ui.screens.main.home.story

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.MotionEvent
import android.widget.RelativeLayout
import androidx.core.view.setMargins
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.dpToPx
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.screen_create_story.*

class CreateStoryScreen : BaseFragment(R.layout.screen_create_story) {

    private var timer: CountDownTimer? = null
    private var timeRemaining = 0L
    private var timeFinished = 0L

    private val recordTime = 10;

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize() {

        startRecordLayout.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_DOWN) {
                changeView()
                timer = getTimer(timeRemaining)
                timer?.start()
            }
            info.showGone(event?.action != MotionEvent.ACTION_DOWN)
            true
        }
    }

    private fun changeView() {
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(dpToPx(mainActivity, 15))
        recordStartBtn.layoutParams = lp

        progressBarBg.visible()

        startRecordLayout.setBackgroundResource(0)

    }

    private fun getTimer(time: Long = 0) = object :
        CountDownTimer(if (time > 0) time else recordTime.times(1000.toLong()), 50) {
        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {
            timeRemaining = millisUntilFinished
            val numerator = recordTime.times(1000.toLong()) - millisUntilFinished
            timeFinished = numerator
            progressBar.progress =
                (numerator * 100 / recordTime.times(1000.toLong())).toInt()
        }
    }

}