package com.snatap.myway.ui.screens.main.home.podcast

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Audio
import com.snatap.myway.network.models.AudioPlaylist
import com.snatap.myway.network.models.AudioPlaylistResp
import com.snatap.myway.ui.adapters.PodcastChapterAdapter
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_podcast_info.*

class PodcastInfoScreen : BaseFragment(R.layout.screen_podcast_info) {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var currentAudio: Audio

    companion object {
        private var data: AudioPlaylist? = null
        fun newInstance(data: AudioPlaylist): PodcastInfoScreen {
            this.data = data
            return PodcastInfoScreen()
        }
    }

    override fun initialize() {

        initViews()

        initClicks()

        checkMedia()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        data?.let {
            title.text = it.title
            desc.text = it.subtitle
            image.loadImage(it.photo)

            right.setImageResource(if (it.is_bookmarked) R.drawable.ic_marked_white else R.drawable.ic_mark_white)

            it.audio_items?.let { audio ->
                count.text = "${audio.size} выпуска"
                recyclerChapters.adapter = PodcastChapterAdapter {
                    currentAudio = it
                    play(it.audio)
                    addFragment(PodcastPlayScreen.newInstance(it.title, it, mediaPlayer))
                }.apply { setData(ArrayList(audio)) }
            }
        }
    }

    private fun initClicks() {

        right.setOnClickListener { viewModel.bookmarkPlaylist(data!!.id) }

        back.setOnClickListener { finishFragment() }

        musicScreenBtn.setOnClickListener {
            addFragment(PodcastPlayScreen.newInstance(data!!.title, currentAudio, mediaPlayer))
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

    private fun checkMedia() {

    }

    private fun play(url: String) {
        mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(url))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer.playbackParams = mediaPlayer.playbackParams.setSpeed(1f)
        }

        musicLayer.visible()
        mediaPlayer.seekTo(0)
        mediaPlayer.start()

        currentPlaylistName.text = data!!.title
        currentTrackName.text = currentAudio.title
    }

    override fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is AudioPlaylistResp) {
                data = it.audio_playlist
                initViews()
            }

            if (it == Constants.PLAYING)
                playBtn.setImageResource(
                    if (mediaPlayer.isPlaying) R.drawable.ic_white_pause else R.drawable.ic_white_play
                )
        })
    }
}