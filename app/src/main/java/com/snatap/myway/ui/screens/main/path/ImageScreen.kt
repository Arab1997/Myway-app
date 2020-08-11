package com.snatap.myway.ui.screens.main.path

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.screen_image.*

class ImageScreen : BaseFragment(R.layout.screen_image) {

    companion object {
        private var url: String = ""
        fun newInstance(url: String): ImageScreen {
            this.url = url
            return ImageScreen()
        }
    }

    override fun initialize() {

        close.setOnClickListener { finishFragment() }

        img.loadImage(url)
    }

}