package com.snatap.myway.ui.screens.main.home.story

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_story.*
import kotlinx.android.synthetic.main.screen_path.pager

class StoriesFragment : BaseFragment(R.layout.fragment_story) {

    private var currentStoryIndex: Int = 0
    private var progressBars: ArrayList<ProgressBar> = arrayListOf()
    private var timeRemaining = 0L
    private var timeFinished = 0L
    private var timer: CountDownTimer? = null
    private var data = arrayListOf<Any>(1, 2, 3, 4, 5, 6)
    private var listener: SnappyStoryListener? = null
    private var isLongClickNext = false
    private var isLongClickLeft = false

    override fun initialize() {

        pager.adapter =
            HistoryPagerAdapter(data, childFragmentManager)



        load(data)
    }

    private fun next(pos: Int) {
        pager.setCurrentItem(pos, true)
        loadImage()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadImage() {
        timer = getTimer()
        pager.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_DOWN) {
                timer?.cancel()
            }
            if (event?.action == MotionEvent.ACTION_UP) {
                timer = getTimer(timeRemaining)
                timer?.start()
            }
            true
        }
        rightLayer.setOnLongClickListener { it ->
            isLongClickNext = true
            timer?.cancel()
            hideViews(true)
            false
        }
        rightLayer.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_UP) {
                if (isLongClickNext) {
                    timer = getTimer(timeRemaining)
                    timer?.start()
                    showViews(true)
                    isLongClickNext = false
                }
            }
            false
        }

        leftLayer.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_UP) {
                if (isLongClickLeft) {
                    timer = getTimer(timeRemaining)
                    timer?.start()
                    showViews()
                    isLongClickLeft = false
                }
            }
            false
        }
        leftLayer.setOnLongClickListener { it ->
            isLongClickLeft = true
            timer?.cancel()
            hideViews()
            false
        }


        timer?.start()
    }

    private fun showViews(isRight: Boolean = false){
        bottomLayer.animate().alphaBy(0f).alpha(1f).duration = 1000
        progress_counter.animate().alphaBy(0f).alpha(1f).duration = 1000

        if (isRight){
            rightLayer.animate().alphaBy(1f).alpha(0f).duration = 1000
        } else {
            leftLayer.animate().alphaBy(1f).alpha(0f).duration = 1000
        }
    }

    private fun hideViews(isRight: Boolean = false){
        bottomLayer.animate().alphaBy(1f).alpha(0f).duration = 1000
        progress_counter.animate().alphaBy(1f).alpha(0f).duration = 1000

        if (isRight){
            rightLayer.animate().alphaBy(0f).alpha(1f).duration = 1000
        } else {
            leftLayer.animate().alphaBy(0f).alpha(1f).duration = 1000
        }
    }

    private fun getTimer(time: Long = 0) = object :
        CountDownTimer(if (time > 0) time else 4.times(1000.toLong()) ?: 0, 50) {
        override fun onFinish() {
            if (currentStoryIndex < data.size - 1) {
                listener?.onFinished(currentStoryIndex)
                currentStoryIndex++
                next(currentStoryIndex)
            } else {
                listener?.onAllFinished()
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            timeRemaining = millisUntilFinished
            val numerator = (4.times(1000.toLong()) ?: 0) - millisUntilFinished
            timeFinished = numerator
            progressBars[currentStoryIndex].progress =
                (numerator * 100 / (4.times(1000.toLong()) ?: 0)).toInt()
        }
    }

    fun load(
        stories: ArrayList<Any> = arrayListOf(),
        listener: SnappyStoryListener? = null
    ) {
        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
        progress_counter.removeAllViews()
        for (story in stories) {
            val view = inflater?.inflate(R.layout.item_history_menu, null) as ProgressBar
            progressBars.add(view)
            progress_counter.addView(view)
        }
        progress_counter.visible()
        this.data = stories
        this.listener = listener
        if (data.isNotEmpty()) {
            listener?.onStart()
            next(0)
            rightLayer.setOnClickListener {
                if (currentStoryIndex < data.size - 1) {
                    timer?.cancel()
                    this.progressBars[currentStoryIndex].progress = 100
                    currentStoryIndex++
                    next(currentStoryIndex)
                }
            }
            leftLayer.setOnClickListener {
//                if (currentStoryIndex == 0) {
//                    timer?.cancel()
//                    timer = getTimer(0)
//                    this.progressBars[0].progress = 0
////                    timer?.start()
////                    return@setOnClickListener
//                }
                if (currentStoryIndex > 0 ) {
                    timer?.cancel()
                    this.progressBars[currentStoryIndex].progress = 0
                    currentStoryIndex--;
                    this.progressBars[currentStoryIndex].progress = 0
                    next(currentStoryIndex)
                }
            }
        }
    }


}

interface SnappyStoryListener {
    fun onAllFinished()
    fun onFinished(index: Int)
    fun onStart()
    fun setImageFor(index: Int, story: Any?)
}

class HistoryItem : BaseFragment(R.layout.screen_history) {
    override fun initialize() {
    }

}

class HistoryPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return HistoryItem()
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }
}