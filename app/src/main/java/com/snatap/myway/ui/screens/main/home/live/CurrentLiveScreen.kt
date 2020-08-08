package com.snatap.myway.ui.screens.main.home.live

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
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
import kotlinx.android.synthetic.main.screen_current_live.*
import kotlinx.android.synthetic.main.screen_current_live.recyclerComments
import kotlinx.android.synthetic.main.screen_repeat_live.*

class CurrentLiveScreen : BaseFragment(R.layout.screen_current_live) {

    private lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun initialize() {

        initClicks()
        initViews()
        initStreamingSettings()
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

        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true
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
                    progress.visible()
                } else if (playbackState == Player.STATE_READY) {
                    progress.gone()
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