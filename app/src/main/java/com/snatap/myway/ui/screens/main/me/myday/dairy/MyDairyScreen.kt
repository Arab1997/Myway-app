package com.snatap.myway.ui.screens.main.me.myday.dairy

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.content_rounded_toolbar_black_quiz.*


class MyDairyScreen : BaseFragment(R.layout.screen_my_dairy) {
    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): MyDairyScreen {
            this.txtTitle = txtTitle
            return MyDairyScreen()
        }
    }
    override fun initialize() {
        setClicks()
        initViews()
    }

    private fun initViews() {
       /* val seekBar = IndicatorSeekBar
            .with(requireContext())
            .max(110f)
            .min(10f)
            .progress(53f)
            .tickCount(5)
            .showTickMarksType(TickMarkType.OVAL)
            .tickMarksColor(resources.getColor(R.color.yellow, null))
            .tickMarksSize(13) //dp
            .showTickTexts(true)
            .tickTextsColor(resources.getColor(R.color.colorPrimary))
            .tickTextsSize(13) //sp
            .tickTextsTypeFace(Typeface.MONOSPACE)
            .showIndicatorType(IndicatorType.ROUNDED_RECTANGLE)
            .indicatorColor(resources.getColor(R.color.white))
            .indicatorTextColor(Color.parseColor("#ffffff"))
            .indicatorTextSize(13) //sp
            .thumbColor(resources.getColor(R.color.black, null))
            .thumbSize(14)
            .trackProgressColor(resources.getColor(R.color.blue, null))
            .trackProgressSize(4)
            .trackBackgroundColor(resources.getColor(R.color.green))
            .trackBackgroundSize(2)
            .onlyThumbDraggable(false)
            .build()*/
    }

    private fun setClicks() {
        title.text = "Дневник"
        //send.setOnClickListener { addFragment(MyDayScreen()) }
        close.setOnClickListener { finishFragment() }

    }

}


