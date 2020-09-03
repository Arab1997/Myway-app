package com.snatap.myway.ui.screens.main.path

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.screen_image.*

class ImageScreen : BaseFragment(R.layout.screen_image) {

    companion object {
        private var url: String = ""
        private var showPublishBtn: Boolean = false
        fun newInstance(url: String): ImageScreen {
            this.url = url
            return ImageScreen()
        }

        fun newInstance(url: String, showPublishBtn: Boolean): ImageScreen {
            this.url = url
            this.showPublishBtn = showPublishBtn
            return ImageScreen()
        }
    }

    private lateinit var publishListener: () -> Unit
    fun setPublishListener(publishListener: () -> Unit) {
        this.publishListener = publishListener
    }

    override fun initialize() {

        close.setOnClickListener { finishFragment() }

        img.loadImage(url)

        publish.showGone(showPublishBtn)

        publish.setOnClickListener { publishListener.invoke() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showPublishBtn = false
    }
}