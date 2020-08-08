package com.snatap.myway.ui.screens.main.home.live

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.LiveCommentAdapter
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.screen_current_live.*

class LiveScreen : BaseFragment(R.layout.screen_current_live) {

    companion object {
        private var isCurrentLiveStream: Boolean = false
        fun newInstance(isCurrentLiveStream: Boolean): LiveScreen {
            this.isCurrentLiveStream = isCurrentLiveStream
            return LiveScreen()
        }
    }

    override fun initialize() {

        initClicks()

        initRecycler()
    }

    private fun initRecycler() {

        recyclerBlack.adapter = LiveCommentAdapter(false).apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8))
        }
        recyclerWhite.adapter = LiveCommentAdapter(true).apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8))
        }
    }

    private fun initClicks() {

        resize.setOnClickListener {
            changeViewOnResize(recyclerBlack.isVisible)
        }
    }

    private fun changeViewOnResize(setWhite: Boolean) {
        val color =
            ContextCompat.getColor(requireContext(), if (setWhite) R.color.white else R.color.hint)

        if (setWhite) {
            commentEdt.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            like.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            resize.setBackgroundResource(R.drawable.rounded_black_transparent_card)
        } else {
            commentEdt.setBackgroundResource(R.drawable.rounded_edt_card)
            like.setBackgroundResource(R.drawable.rounded_edt_card)
            resize.setBackgroundResource(R.drawable.rounded_edt_card)
        }

        gradient.showGone(setWhite)
        recyclerWhite.showGone(setWhite)
        recyclerBlack.showGone(!setWhite)

        like.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        resize.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        resize.setImageResource(if (setWhite) R.drawable.ic_resize_off else R.drawable.ic_resize_on)

        commentEdt.setTextColor(color)
        commentEdt.setHintTextColor(color)
    }
}