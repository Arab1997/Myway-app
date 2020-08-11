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
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.screen_podcast_info.*
import kotlinx.android.synthetic.main.screen_podcast_play.*
import java.util.*

class PodcastInfoScreen() : BaseFragment(R.layout.screen_podcast_info) {

    private var mediaPlayer: MediaPlayer? = null
    private var runnable: Runnable? = null
    private var handler: Handler? = null

    companion object{
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): PodcastInfoScreen{
            this.txtTitle = txtTitle
            return PodcastInfoScreen()
        }

        public fun stringForTime(timeMs: Int): String? {
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
        onReceiveData()
    }

    private fun onReceiveData(){
        viewModel.data.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it is Bundle){
                val bundle: Bundle = it

                val time = bundle.getInt("time")
                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musicc)
                mediaPlayer!!.seekTo(time)
                mediaPlayer!!.setOnPreparedListener {
                    mediaPlayer!!.start()
                    changeSeekBar()
                }
                handler = Handler()
            }
        })
    }


    private fun changeSeekBar() {

        if (mediaPlayer!!.isPlaying) {
            runnable = Runnable { changeSeekBar() }
            handler!!.postDelayed(runnable, 50)
        }
    }

    private fun initViews() {
        title.text = txtTitle

        back.setOnClickListener { finishFragment() }

        info.text = ""
        info.setBackgroundResource(R.drawable.ic_mark_white)

        recyclerChapters.adapter = PodcastChapterAdapter {
            if (mediaPlayer != null)
                mediaPlayer!!.stop()
            addFragment(PodcastPlayScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        }

        lastMusicLayer.setOnClickListener {
            PodcastPlayScreen.newInstance(mediaPlayer!!.currentPosition)
        }
    }

}