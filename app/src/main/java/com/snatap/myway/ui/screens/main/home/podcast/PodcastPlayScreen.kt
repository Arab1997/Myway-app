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

    private lateinit var mediaPlayer: MediaPlayer
    private var runnable: Runnable? = null
    private var handler: Handler? = null
    private var playerSpeed:Float = 1f

    companion object {
        private var currentPos: Int = 0
        public fun newInstance(currentPos: Int){
            this.currentPos = currentPos
        }
    }

    override fun initialize() {

        initViews()
        initClicks()
        seekBarListener()
    }

    override fun onDestroy() {
        if (handler != null)
            handler!!.removeCallbacks(runnable)
        mediaPlayer.stop()
        val bundle = Bundle()
        bundle.putInt("time", mediaPlayer.currentPosition)
        viewModel.data.value = bundle
        super.onDestroy()
    }

    private fun initViews() {


        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musicc)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer.playbackParams = mediaPlayer.playbackParams.setSpeed(playerSpeed)
        }
        mediaPlayer.seekTo(currentPos)
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            mediaPlayer.start()
            changeSeekBar()
        }
        handler = Handler()

        txtEndTime.text = stringForTime(mediaPlayer.duration)

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
            handler!!.postDelayed(runnable, 50)
        }
    }

}