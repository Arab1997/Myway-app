package com.snatap.myway.ui.screens.main.home.history

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.setMargins
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
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
            if (event?.action == MotionEvent.ACTION_UP) {
                addFragment(StoryFragment())
                timer?.cancel()
            }
            true
        }
//        timer?.start()
    }

    private fun changeView() {
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(dpToPx(15f).toInt())
        recordStartBtn.layoutParams = lp

        progressBarBg.visibility = View.VISIBLE

        startRecordLayout.setBackgroundResource(0)

        info.visibility = View.GONE
    }

    private fun dpToPx(dip: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }

    private fun getTimer(time: Long = 0) = object :
        CountDownTimer(if (time > 0) time else recordTime.times(1000.toLong()) ?: 0, 50) {
        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {
            timeRemaining = millisUntilFinished
            val numerator = (recordTime.times(1000.toLong()) ?: 0) - millisUntilFinished
            timeFinished = numerator
            progressBar.progress =
                (numerator * 100 / (recordTime.times(1000.toLong()) ?: 0)).toInt()
        }
    }

}