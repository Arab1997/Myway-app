package com.snatap.myway.ui.screens.main.home.podcast

import android.media.MediaPlayer
import android.os.Handler
import android.widget.SeekBar
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.screen_podcast_play.*
import java.util.*

class PodcastPlayScreen : BaseFragment(R.layout.screen_podcast_play) {

    private lateinit var mediaPlayer: MediaPlayer
    private var runnable: Runnable? = null
    private var handler: Handler? = null

    override fun initialize() {

        initViews()
        initClicks()
        seekBarListener()

    }

    private fun initViews() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musicc)
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            mediaPlayer.start()
            changeSeekBar()
        }
        handler = Handler()

        txtEndTime.text = stringForTime(mediaPlayer.duration)
    }

    private fun seekBarListener(){
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun initClicks() {
        play.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                play.setImageResource(R.drawable.ic_white_play)
            } else {
                mediaPlayer.start()
                play.setImageResource(R.drawable.ic_white_pause)
            }
        }

        forwardBtn.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition+30000)
        }

        backBtn.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition-30000)
        }
    }

    private fun changeSeekBar(){
        txtCurrentTime.text = stringForTime(mediaPlayer.currentPosition)
        seekBar.progress = mediaPlayer.currentPosition

        if (mediaPlayer.isPlaying){
            runnable = Runnable { changeSeekBar() }
            handler!!.postDelayed(runnable, 50)
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

}