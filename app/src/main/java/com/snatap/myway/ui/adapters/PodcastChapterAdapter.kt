package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Audio
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_podcast_chapter.view.*


class PodcastChapterAdapter(private val listener: (Audio) -> Unit) :
    BaseAdapter<Audio>(R.layout.item_podcast_chapter) {

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Audio) {
        holder.itemView.apply {
            data.apply {
                setOnClickListener { listener.invoke(this) }

                name.text = title
                image.loadImage(photo)

                val m = (duration / 60 / 60) % 60
                val h = (duration / 60 / 60 / 60) % 60
                dur.text = "$h час $m мин"
            }
        }
    }

}