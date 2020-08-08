package com.snatap.myway.ui.screens.main.home.live

import android.net.Uri
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.LiveCommentAdapter
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.custom_controller.*
import kotlinx.android.synthetic.main.custom_controller.view.*
import kotlinx.android.synthetic.main.screen_repeat_live.*
import timber.log.Timber

class RepeatLiveScreen : BaseFragment(R.layout.screen_repeat_live) {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    var flag: Boolean = true
    var isPlay: Boolean = true

    override fun initialize() {
        recyclerComments.adapter = LiveCommentAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14))
        }
        recyclerComments.adapter = LiveCommentAdapter(true).apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14))
        }

        initStreamingSettings()
        initClicks()
    }

    private fun initClicks() {
        btnFullScreen.setOnClickListener {
            if (flag) {
                btnFullScreen.setImageResource(R.drawable.ic_resize_on)

                val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                videoPlayer.layoutParams = params
                recyclerCommentsTrans.gone()
                flag = false
            } else {
                btnFullScreen.setImageResource(R.drawable.ic_resize_off)

                val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
                videoPlayer.layoutParams = params
                recyclerCommentsTrans.visible()

                flag = true
            }
        }

        rewind.setOnClickListener {
            simpleExoPlayer.apply { seekTo(currentPosition - 3000) }
        }

        forward.setOnClickListener {
            simpleExoPlayer.apply { seekTo(currentPosition + 3000) }
        }


    }

    private fun initStreamingSettings() {
        val videoUri: Uri =
            Uri.parse("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4");

        val loadControl: LoadControl = DefaultLoadControl()
        val bandWithMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWithMeter))
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            context, trackSelector, loadControl
        )
        val factory = DefaultHttpDataSourceFactory(
            "exoplayer_video"
        )
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ExtractorMediaSource(videoUri, factory, extractorsFactory, null, null)

        videoPlayer.player = simpleExoPlayer
        videoPlayer.keepScreenOn = true
        simpleExoPlayer.prepare(mediaSource)
        simpleExoPlayer.playWhenReady = true

        simpleExoPlayer.addListener(object : Player.EventListener {
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
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState
    }

    override fun onResume() {
        super.onResume()
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.playbackState
    }
}