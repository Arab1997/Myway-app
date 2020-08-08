package com.snatap.myway.ui.screens.main.home.live

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.LiveCommentAdapter
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.custom_controller.*
import kotlinx.android.synthetic.main.screen_live.*

class LiveScreen : BaseFragment(R.layout.screen_live) {

    companion object {
        private var isCurrentLiveStream: Boolean = false
        fun newInstance(isCurrentLiveStream: Boolean): LiveScreen {
            this.isCurrentLiveStream = isCurrentLiveStream
            return LiveScreen()
        }
    }

    private lateinit var exoPlayer: SimpleExoPlayer
    override fun initialize() {

        initClicks()

        initRecycler()

        initStreaming()
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

        btnFullScreen.setOnClickListener {
            resize.performClick()
        }

        rewind.setOnClickListener {
            exoPlayer.apply { seekTo(currentPosition - 30000) }
        }

        forward.setOnClickListener {
            exoPlayer.apply { seekTo(currentPosition + 30000) }
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
        btnFullScreen.setImageResource(if (setWhite) R.drawable.ic_resize_off else R.drawable.ic_resize_on)

        commentEdt.setTextColor(color)
        commentEdt.setHintTextColor(color)
    }

    private fun initStreaming(url: String = "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4") {

        val loadControl: LoadControl = DefaultLoadControl()
        val bandWithMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWithMeter))
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)

        val userAgent = Util.getUserAgent(context, requireContext().getString(R.string.app_name))
        val mediaSource = ProgressiveMediaSource
            .Factory(DefaultDataSourceFactory(context, userAgent))
            .createMediaSource(Uri.parse(url))

        exoPlayer.prepare(mediaSource)
        videoPlayer.player = exoPlayer
        videoPlayer.requestFocus()

        exoPlayer.playWhenReady = true

        videoPlayer.useController = !isCurrentLiveStream
        bottom.showGone(isCurrentLiveStream)

        exoPlayer.addListener(object : Player.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            }

            override fun onSeekProcessed() {
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPositionDiscontinuity(reason: Int) {
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.visible()
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.gone()
                }
            }

        })
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