package com.snatap.myway.ui.screens.main.home.podcast

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.widget.SeekBar
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Audio
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.screen_podcast_play.*
import java.util.*

class PodcastPlayScreen : BaseFragment(R.layout.screen_podcast_play) {

    companion object {
        private var playlistName: String = ""
        private lateinit var data: Audio
        private lateinit var mediaPlayer: MediaPlayer
        fun newInstance(
            playlistName: String,
            data: Audio,
            mediaPlayer: MediaPlayer
        ): PodcastPlayScreen {
            this.data = data
            this.playlistName = playlistName
            this.mediaPlayer = mediaPlayer
            return PodcastPlayScreen()
        }
    }

    private var handler = Handler()
    private var runnable = Runnable {}
    private var playerSpeed: Float = 1f

    override fun initialize() {

        checkMedia()

        initViews()

        initClicks()

        seekBarListener()
    }

    private fun checkMedia() {
        play.setImageResource(
            if (mediaPlayer.isPlaying) R.drawable.ic_white_pause else R.drawable.ic_white_play
        )
    }

    private fun initViews() {

        playList.text = playlistName
        image.loadImage(data.photo)
        name.text = data.title

        seekBar.max = mediaPlayer.duration

        txtEndTime.text = stringForTime(mediaPlayer.duration)

        changeSeekBar()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) speedUpLayer.gone()
    }

    private fun seekBarListener() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun initClicks() {

        close.setOnClickListener { finishFragment() }

        play.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                play.setImageResource(R.drawable.ic_white_play)
            } else {
                mediaPlayer.start()
                changeSeekBar()
                play.setImageResource(R.drawable.ic_white_pause)
            }
        }

        forwardBtn.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition + 30000)
        }

        backBtn.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition - 30000)
        }

        speedUp.setOnClickListener {
            if (playerSpeed < 2f) playerSpeed += 0.5f
            else playerSpeed = 1f

            speedUp.text = "${playerSpeed}X"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer.playbackParams = mediaPlayer.playbackParams.setSpeed(playerSpeed)
            }
        }
    }

    private fun changeSeekBar() {
        txtCurrentTime.text = stringForTime(mediaPlayer.currentPosition)
        seekBar.progress = mediaPlayer.currentPosition

        if (mediaPlayer.isPlaying) {
            runnable = Runnable { changeSeekBar() }
            handler.postDelayed(runnable, 50)
        }
    }

    private fun stringForTime(timeMs: Int): String? {
        val mFormatter: Formatter
        val mFormatBuilder: StringBuilder = StringBuilder()
        mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        if (mediaPlayer.isPlaying) viewModel.data.value = Constants.PLAYING
    }
}