package com.snatap.myway.ui.screens.splash

import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.view.View
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.auth.auth.AuthLoginScreen
import com.snatap.myway.ui.screens.auth.auth.AuthPhoneScreen
import kotlinx.android.synthetic.main.screen_splash.*


class SplashScreen : BaseFragment(R.layout.screen_splash) {

    override fun initialize() {

        continueView.setOnClickListener { playPauseVideo(true) }

        close.setOnClickListener { playPauseVideo(false) }

        register.setOnClickListener { addFragment(AuthPhoneScreen.newInstance(true)) }

        haveAccount.setOnClickListener { addFragment(AuthLoginScreen()) }

        initVideoView()

        playPauseVideo(play = true, immediate = true)
    }

    private fun initVideoView() {
        val src = "android.resource://" + requireContext().packageName + "/" + R.raw.video
        val video = Uri.parse(src)
        videoView.setVideoURI(video)
        videoView.setOnPreparedListener {
            it.isLooping = true

            val videoRatio = (it.videoWidth / it.videoHeight).toFloat()
            val screenRatio = videoView.width / videoView.height.toFloat()
            val scaleX = videoRatio / screenRatio
            if (scaleX >= 1f) {
                videoView.scaleX = scaleX
            } else {
                videoView.scaleY = 1f / scaleX
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            videoView.setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)


    }

    private fun playPauseVideo(play: Boolean, immediate: Boolean = false) {
        // hide view
        var duration = 300.toLong()
        if (immediate) duration = 0
        close.showHideAnimation(play, duration)
        continueView.showHideAnimation(!play, duration)
        register.showHideAnimation(!play, duration)
        haveAccount.showHideAnimation(!play, duration)

        if (play) videoView.start() else videoView.pause()
    }

    private fun View.showHideAnimation(show: Boolean, duration: Long = 300) {
        this.animate().alpha(if (show) 1f else 0f).setDuration(duration).start()
    }
}