package com.snatap.myway.ui.screens.main.home.live

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.BehindLiveWindowException
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.LastStream
import com.snatap.myway.network.models.Stream
import com.snatap.myway.network.models.StreamUrlResp
import com.snatap.myway.ui.adapters.LiveCommentAdapter
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_live_header.*
import kotlinx.android.synthetic.main.screen_live_streams.*
import kotlinx.android.synthetic.main.stream_controller.*
import org.jsoup.Jsoup
import java.util.*
import kotlin.properties.Delegates

class LiveStreamScreen : BaseFragment(R.layout.screen_live_streams) {

    companion object {
        private var isCurrentLiveStream: Boolean = false
        private var streamData: Stream? = null
        private var lastStream: LastStream? = null
        fun newInstance(isCurrentLiveStream: Boolean, stream: Stream): LiveStreamScreen {
            this.isCurrentLiveStream = isCurrentLiveStream
            this.streamData = stream
            return LiveStreamScreen()
        }
    }

    private var urlRequest = false
    private var script: String by Delegates.observable("") { property, oldValue, newValue ->
        streamData?.id?.let {
            if (htmlContent.isNotEmpty() && script.isNotEmpty()) {
                urlRequest = true
                viewModel.getStreamUrl(it, htmlContent, script)
            }
        }
    }

    private var htmlContent: String by Delegates.observable("") { property, oldValue, newValue ->
        streamData?.id?.let {
            if (htmlContent.isNotEmpty() && script.isNotEmpty()) {
                urlRequest = true
                viewModel.getStreamUrl(it, htmlContent, script)
            }
        }
    }

    private fun fetchLiveUrl() {
        if (lastStream != null && streamData!!.id == lastStream!!.id) {
            play(lastStream!!.url)
        } else newThread {
            //Connect to website
            val doc = Jsoup.connect(streamData!!.youtube_url).get()
            htmlContent = doc.html()

            val scriptElements = doc.getElementsByTag("script")
            scriptElements.forEach {
                if (it.attributes().get("src").contains("/s/player")) {
                    fetchScriptUrl("https://youtube.com" + it.attributes().get("src"))
                    return@forEach
                }
            }
        }
    }

    private fun fetchScriptUrl(scriptUrl: String) {
        newThread {
            //Connect to website
            loge(scriptUrl)
            val doc = Jsoup.connect(scriptUrl).get()
            script = doc.html()
        }
    }

    private lateinit var exoPlayer: SimpleExoPlayer
    override fun initialize() {

        initStreaming()

        fetchLiveUrl()

        initClicks()

        initData()
//        play("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4")
    }

    private lateinit var whiteAdapter: LiveCommentAdapter
    private lateinit var blackAdapter: LiveCommentAdapter
    private fun initData() {

        streamData?.apply {
            userImg.loadImage(author_avatar)
            name.text = author_name
        }
        whiteAdapter = LiveCommentAdapter(true)
        blackAdapter = LiveCommentAdapter(false)

        recyclerBlack.adapter = blackAdapter
        recyclerWhite.adapter = whiteAdapter

        commentEdt.addTextChangedListener {
            send.showGone(it.toString().isNotEmpty())
        }

        liveBtn.showGone(isCurrentLiveStream)
    }

    private fun initClicks() {
        close.setOnClickListener { finishFragment() }

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

        send.setOnClickListener {
            commentEdt.text.toString().let {
                if (it.isNotEmpty() && streamData != null) {
                    commentEdt.text?.clear()
                    viewModel.sendStreamMessage(streamData!!.id, it)
                }
            }
        }
    }

    private fun changeViewOnResize(setWhite: Boolean) {
        val color =
            ContextCompat.getColor(requireContext(), if (setWhite) R.color.white else R.color.hint)

        if (setWhite) {
            commentsLayout.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            like.setBackgroundResource(R.drawable.rounded_black_transparent_card)
            resize.setBackgroundResource(R.drawable.rounded_black_transparent_card)
        } else {
            commentsLayout.setBackgroundResource(R.drawable.rounded_edt_card)
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

    private fun initStreaming() {

        val loadControl: LoadControl = DefaultLoadControl()
        val bandWithMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWithMeter))
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)

        videoPlayer.player = exoPlayer
        videoPlayer.requestFocus()

        exoPlayer.playWhenReady = true

        videoPlayer.useController = !isCurrentLiveStream
        bottom.showGone(isCurrentLiveStream)

        exoPlayer.addListener(object : PlayerListener {
            override fun onStateChanged(isReady: Boolean) {
                progressBar.showGone(!isReady)
            }

            override fun onPlayingChanged(isPlaying: Boolean) {
            }

            override fun onError(error: ExoPlaybackException?) {
                loge(error?.localizedMessage.toString())
                if (error?.cause is BehindLiveWindowException) {
                    initStreaming()
                    lastStream?.url?.let { play(it) }
                } else {
                    progressBar.gone()
                    toast(requireContext(), R.string.smth_wrong)
                    error?.printStackTrace()
                }
            }
        })
    }

    private fun play(url: String) {
        lastStream = LastStream(streamData!!.id, url)
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

    override fun observe() {
        viewModel.apply {
            streamData?.let {
                getStreamMessages(it.id)
                if (isCurrentLiveStream) joinStream(it.id)
            }

            streamsMessages.observe(viewLifecycleOwner, Observer {
                blackAdapter.setData(ArrayList(it.reversed()))
                whiteAdapter.setData(ArrayList(it.reversed()))
                recyclerBlack.layoutManager?.scrollToPosition(0)
                recyclerWhite.layoutManager?.scrollToPosition(0)
            })
            data.observe(viewLifecycleOwner, Observer {
                if (urlRequest && it is StreamUrlResp) {
                    urlRequest = false
                    play(it.url)
                }
            })
        }
    }
}

interface PlayerListener : Player.EventListener {
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        onStateChanged(playbackState == Player.STATE_READY)
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        onError(error)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        onPlayingChanged(isPlaying)
    }

    fun onStateChanged(isReady: Boolean)
    fun onPlayingChanged(isPlaying: Boolean)
    fun onError(error: ExoPlaybackException?)
}