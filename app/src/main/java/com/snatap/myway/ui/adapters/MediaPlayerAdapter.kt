package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Training
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_media_player.view.*

class MediaPlayerAdapter(private val listener: (Int) -> Unit) :
    BaseAdapter<Training>(R.layout.item_media_player) {

    override fun bindViewHolder(holder: ViewHolder, data: Training) {
        holder.itemView.apply {
            data.apply {
                image.loadImage(photo)
                name.text = title
                dur.text = duration
                live.showGone(playing)
                setOnClickListener { listener.invoke(holder.adapterPosition) }
            }
        }
    }
}