package com.snatap.myway.ui.screens.main.home.media

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Handler
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
import com.snatap.myway.network.models.Training
import com.snatap.myway.ui.adapters.MediaPlayerAdapter
import com.snatap.myway.ui.screens.main.home.live.PlayerListener
import com.snatap.myway.utils.extensions.*
import com.snatap.myway.utils.views.CountDownAnimation
import io.reactivex.Observable
import kotlinx.android.synthetic.main.screen_media_player.*
import java.util.*
import java.util.concurrent.TimeUnit


class MediaPlayerScreen : BaseFragment(R.layout.screen_media_player) {

    companion object {
        private var url: String = "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"
        private var videoName: String = ""
        private var trainings = arrayListOf<Training>()
        fun newInstance(url: String, videoName: String): MediaPlayerScreen {
            this.url = url
            this.videoName = videoName
            return MediaPlayerScreen()
        }

        fun newInstance(
            url: String, videoName: String, trainings: ArrayList<Training>
        ): MediaPlayerScreen {
            this.url = url
            this.videoName = videoName
            this.trainings = trainings
            return MediaPlayerScreen()
        }

    }

    private var playbackProgressObservable: Observable<Long> =
        Observable.interval(1, TimeUnit.SECONDS)
            .map { exoPlayer.currentPosition }

    private val timerTime = 4
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var adapter: MediaPlayerAdapter
    override fun initialize() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        initData()

        startAnimation()

        initPlayer()
    }

    private fun initData() {
        next.setOnClickListener {
            currentPos++
            updatePlayingItem(currentPos)
        }

        finish.setOnClickListener { finishFragment() }

        adapter = MediaPlayerAdapter {
            updatePlayingItem(it)
        }.apply { setData(trainings) }

        recycler.adapter = adapter

        name.text = videoName

        next.invisible()
        finish.invisible()
        recycler.invisible()
    }

    private fun startAnimation() {
        progressBar.gone()
        val anim = CountDownAnimation(timer, timerTime)
        anim.setAlphaAnimation(false)
        anim.start()

        Handler().postDelayed({
            play(url)
            showHideContent(false)
        }, (timerTime * 1000).toLong())
    }

    private fun showHideContent(show: Boolean) {
        gradient.showGone(show)
        name.showGone(show)
        if (trainings.isNotEmpty()) {
            next.showGone(show)
            finish.showGone(show)
            recycler.showGone(show)
        }
    }

    @SuppressLint("CheckResult")
    private fun initPlayer() {

        val loadControl: LoadControl = DefaultLoadControl()
        val bandWithMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWithMeter))
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)

        videoPlayer.player = exoPlayer
        videoPlayer.requestFocus()
        videoPlayer.useController = false
        Handler().postDelayed({
            mainActivity.runOnUiThread { videoPlayer.useController = true }
        }, 5000)
        exoPlayer.addListener(object : PlayerListener {
            override fun onStateChanged(isReady: Boolean) {
                progressBar.showGone(!isReady)
            }

            override fun onPlayingChanged(isPlaying: Boolean) {
//                showHideContent(!isPlaying)
            }

            override fun onError(error: ExoPlaybackException?) {
                progressBar.gone()
                toast(requireContext(), R.string.smth_wrong)
                error?.printStackTrace()
                loge(error?.localizedMessage.toString())
            }
        })
        videoPlayer.setControllerVisibilityListener {
            showHideContent(it == 0)
        }

        playbackProgressObservable.subscribe { progress ->
            if (exoPlayer.duration - progress <= 100.toLong() && trainings.isNotEmpty()) {
                if (trainings.any { it.playing }) {
                    val tasks = sharedManager.finishedTasks
                    tasks.add(trainings.first().id)
                    sharedManager.finishedTasks = tasks
                }
            }
        }
    }

    private var currentPos = 0
    private fun updatePlayingItem(pos: Int) {
        currentPos = pos
        trainings.forEach { it.playing = false }
        trainings[pos] = trainings[pos].apply { playing = true }
        adapter.setData(trainings)
        recycler.scrollToPosition(pos)

        play(trainings[pos].video)
        next.showGone(pos != trainings.lastIndex)
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

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        trainings = arrayListOf()
        videoName = ""
    }

}