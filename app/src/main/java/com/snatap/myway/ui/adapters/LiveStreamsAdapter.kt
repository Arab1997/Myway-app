package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Stream
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_live_stream.view.*

class LiveStreamsAdapter(private val listener: (Stream) -> Unit) :
    BaseAdapter<Stream>(R.layout.item_live_stream) {

    override fun bindViewHolder(holder: ViewHolder, data: Stream) {
        holder.itemView.apply {
            data.apply {
                setOnClickListener { listener.invoke(this) }

                userImg.loadImage(author_avatar)
                userName.text = author_name
                userDate.text = created_at.formatTime()
                img.loadImage(photo)
                streamTitle.text = title

                recyclerTags.adapter = TagsAdapter().apply { setData(ArrayList(tags)) }
            }
        }
    }
}