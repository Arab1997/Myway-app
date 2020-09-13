package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Audio
import com.snatap.myway.network.models.AudioPlaylist
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_banner_podcast.view.*


class PodcastBannerAdapter(
    private val playlist: AudioPlaylist,
    private val listener: (AudioPlaylist) -> Unit
) : BaseAdapter<Audio>(R.layout.item_banner_podcast) {

    private var h = 0
    private var m = 0

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Audio) {
        holder.itemView.apply {
            data.apply {

                when {
                    holder.adapterPosition % 4 == 0 -> bannerBackground.setBackgroundResource(R.drawable.rounded_red_card)
                    holder.adapterPosition % 4 == 1 -> bannerBackground.setBackgroundResource(R.drawable.rounded_blue_card)
                    holder.adapterPosition % 4 == 2 -> bannerBackground.setBackgroundResource(R.drawable.rounded_orange_card)
                    holder.adapterPosition % 4 == 3 -> bannerBackground.setBackgroundResource(R.drawable.rounded_violet_card)
                }
                setOnClickListener { listener.invoke(playlist) }
                listen.setOnClickListener { listener.invoke(playlist) }

                bannerImage.loadImage(photo)
                name.text = playlist.title
                currentTrack.text = title

                m = (duration / 60 / 60) % 60
                h = (duration / 60 / 60 / 60) % 60

                dur.text = "$h час $m мин"
            }
        }
    }

}