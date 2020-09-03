package com.snatap.myway.ui.screens.main.common

import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.home.live.PlayerListener
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.loge
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.extensions.toast
import kotlinx.android.synthetic.main.screen_video.*
import java.util.*

class VideoScreen : BaseFragment(R.layout.screen_video) {

    companion object {
        private var url: String = ""
        fun newInstance(url: String): VideoScreen {
            Companion.url = url
            return VideoScreen()
        }
    }

    private lateinit var exoPlayer: SimpleExoPlayer
    override fun initialize() {

        close.setOnClickListener { finishFragment() }

        initStreaming()

        play(url)
    }

    private fun initStreaming() {

        val loadControl: LoadControl = DefaultLoadControl()
        val bandWithMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWithMeter))
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)

        videoPlayer.player = exoPlayer
        videoPlayer.requestFocus()

        exoPlayer.playWhenReady = true

        exoPlayer.addListener(object : PlayerListener {
            override fun onStateChanged(isReady: Boolean) {
                progressBar.showGone(!isReady)
            }

            override fun onPlayingChanged(isPlaying: Boolean) {

            }

            override fun onError(error: ExoPlaybackException?) {
                progressBar.gone()
                toast(requireContext(), R.string.smth_wrong)
                error?.printStackTrace()
                loge(error?.localizedMessage.toString())
            }
        })
    }

    private fun play(url: String) {

        val userAgent = Util.getUserAgent(context, requireContext().getString(R.string.app_name))
        val videoSource = if (url.toUpperCase(Locale.getDefault()).contains("M3U8")) {
            HlsMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                .createMediaSource(Uri.parse(url))
        } else {
            ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                .createMediaSource(Uri.parse(url))
        }

        exoPlayer.prepare(videoSource)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.playWhenReady = false
        exoPlayer.playbackState
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
        exoPlayer.playbackState
    }

}