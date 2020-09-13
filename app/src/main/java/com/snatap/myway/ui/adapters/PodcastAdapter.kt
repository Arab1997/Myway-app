package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.AudioPlaylist
import com.snatap.myway.network.models.Tag
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.loge
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_podcast.view.*

class PodcastAdapter(private val listener: (AudioPlaylist) -> Unit) :
    BaseAdapter<AudioPlaylist>(R.layout.item_podcast) {

    private var iconsList = arrayListOf<Tag>()
    fun setIcons(iconsList: ArrayList<Tag>) {
        this.iconsList = iconsList
        loge("set ${iconsList.size}")
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: AudioPlaylist) {
        holder.itemView.apply {
            data.apply {
                setOnClickListener { listener.invoke(this) }

                image.loadImage(photo)
                name.text = title
                audios.text = "$audio_items_count выпуска"

                icons.adapter = TagIconsAdapter().apply { setData(iconsList) }
                icons.showGone(iconsList.isNotEmpty())
            }
        }
    }

}
