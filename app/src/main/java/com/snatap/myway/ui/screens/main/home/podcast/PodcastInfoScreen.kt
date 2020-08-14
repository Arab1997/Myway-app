package com.snatap.myway.ui.screens.main.home.podcast

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PodcastChapterAdapter
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.screen_podcast_info.*
import kotlinx.android.synthetic.main.screen_podcast_play.*
import java.util.*

class PodcastInfoScreen() : BaseFragment(R.layout.screen_podcast_info) {

    private lateinit var mediaPlayer: MediaPlayer

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): PodcastInfoScreen {
            this.txtTitle = txtTitle
            return PodcastInfoScreen()
        }

        fun stringForTime(timeMs: Int): String? {
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

    override fun initialize() {
        initViews()
        initClicks()
        createMusic()
        checkMedia()
    }

    private fun checkMedia() {
        viewModel.data.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == true){
                if (mediaPlayer.isPlaying) {
                    playBtn.setImageResource(R.drawable.ic_white_pause)
                } else {
                    playBtn.setImageResource(R.drawable.ic_white_play)
                }
            }
        })
    }

    private fun startMusic() {
        musicLayer.visible()
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
    }

    private fun createMusic() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musicc)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer.playbackParams = mediaPlayer.playbackParams.setSpeed(1f)
        }
        mediaPlayer.seekTo(0)
        mediaPlayer.pause()
    }

    private fun initViews() {
        title.text = txtTitle

        back.setOnClickListener { finishFragment() }

        info.text = ""
        info.setBackgroundResource(R.drawable.ic_mark_white)

        recyclerChapters.adapter = PodcastChapterAdapter {
            startMusic()
            addFragment(PodcastPlayScreen.newInstance(mediaPlayer))
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        }

    }

    private fun initClicks() {
        musicScreenBtn.setOnClickListener {
            addFragment(PodcastPlayScreen.newInstance(mediaPlayer))
        }

        playBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playBtn.setImageResource(R.drawable.ic_white_play)
            } else {
                mediaPlayer.start()
                playBtn.setImageResource(R.drawable.ic_white_pause)
            }
        }

        nextBtn.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition + 30000)
        }
    }

}