package com.snatap.myway.ui.screens.main.home.story

import android.annotation.SuppressLint
import com.jackandphantom.instagramvideobutton.InstagramVideoButton
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.utils.extensions.*
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.screen_create_story.*

class CreateStoryScreen : BaseFragment(R.layout.screen_create_story) {

    private var imgFilePath = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize() {

        close.setOnClickListener { finishFragment() }

        changeCamera.setOnClickListener { inDevelopment(requireContext()) }

        addImage.setOnClickListener {
            it.blockClickable()
            TedImagePicker.with(requireContext()).start { uri ->
                imgFilePath = mainActivity.getFilePath(uri)
                val fragment = ImageScreen.newInstance(imgFilePath, true).apply {
                    setPublishListener {
                        // todo
                    }
                }
                removePreviousCallback({ addFragment(fragment) })
            }
        }

        record.actionListener = object : InstagramVideoButton.ActionListener {
            override fun onStartRecord() {
                loge("CALL the on start record ", "MY TAG")
                info.gone()

            }

            override fun onEndRecord() {
                loge("CALL the on end record ", "MY TAG")
                info.visible()
            }

            override fun onSingleTap() {
                loge("CALL the on single tap record ", "MY TAG")
            }

            override fun onDurationTooShortError() {
                loge("CALL the on on duration record ", "MY TAG")

            }

            override fun onCancelled() {
                loge("CALL the on on cancel record ", "MY TAG")
            }


        }
    }
}