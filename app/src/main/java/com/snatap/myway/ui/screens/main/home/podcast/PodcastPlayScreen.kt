package com.snatap.myway.ui.screens.main.home.podcast

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.home.podcast.PodcastInfoScreen.Companion.stringForTime
import com.snatap.myway.utils.extensions.gone
import kotlinx.android.synthetic.main.screen_podcast_play.*

class PodcastPlayScreen : BaseFragment(R.layout.screen_podcast_play) {

    private var runnable: Runnable? = null
    private lateinit var handler: Handler
    private var playerSpeed: Float = 1f

    companion object {
        private lateinit var mediaPlayer: MediaPlayer
        fun newInstance(mediaPlayer: MediaPlayer): PodcastPlayScreen {
            this.mediaPlayer = mediaPlayer
            return PodcastPlayScreen()
        }
    }

    override fun initialize() {
        checkMedia()
        initViews()
        initClicks()
        seekBarListener()
    }

    private fun checkMedia() {
        if (mediaPlayer.isPlaying) {
            play.setImageResource(R.drawable.ic_white_pause)
        } else {
            play.setImageResource(R.drawable.ic_white_play)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        viewModel.data.value = true
        super.onDestroy()
    }

    private fun initViews() {
        seekBar.max = mediaPlayer.duration
        txtEndTime.text = stringForTime(mediaPlayer.duration)
        handler = Handler()
        changeSeekBar()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            speedUpLayer.gone()
        }
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
            if (playerSpeed < 2f)
                playerSpeed += 0.5f
            else
                playerSpeed = 1f

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

}