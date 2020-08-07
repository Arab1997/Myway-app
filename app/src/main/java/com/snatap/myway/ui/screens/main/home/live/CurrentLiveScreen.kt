package com.snatap.myway.ui.screens.main.home.live

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.LiveCommentAdapter
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.screen_current_live.*

class CurrentLiveScreen : BaseFragment(R.layout.screen_current_live) {

    override fun initialize() {

        initClicks()
        initViews()
    }

    private fun initViews(){
        recyclerComments.adapter = LiveCommentAdapter(false).apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8))
        }
        recyclerCommentsOff.adapter = LiveCommentAdapter(true).apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8))
        }
    }

    private fun initClicks() {
        resizeBtn.setOnClickListener {
            changeViewOnResize(layerComments.isVisible)
        }
    }


    private fun changeViewOnResize(isVisible: Boolean) {
        val color: Int =
            ContextCompat.getColor(requireContext(), if (isVisible) R.color.white else R.color.hint)

        layerComments.apply { if (isVisible) gone() else visible() }
        resizeBtn.apply {
            setImageResource(if (isVisible) R.drawable.ic_resize_off else R.drawable.ic_resize_on)
            setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        }
        sendCommentEdt.setTextColor(color)
        sendCommentEdt.setHintTextColor(color)
        if (!isVisible){
            sendCommentEdt.setBackgroundResource(R.drawable.rounded_light_white_card)
            heartBtn.setBackgroundResource(R.drawable.rounded_light_white_card)
            resizeBtn.setBackgroundResource(R.drawable.rounded_light_white_card)
        }
        else{
            sendCommentEdt.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            heartBtn.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            resizeBtn.setBackgroundResource(R.drawable.rounded_black_transparent_card)
        }

        heartBtn.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)

        recyclerCommentsOff.adapter = LiveCommentAdapter(isVisible).apply {
            setData(arrayListOf(1,2,3,4,5,6,7,8))
        }
    }

}