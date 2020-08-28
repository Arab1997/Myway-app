package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Stream
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.content_duration.view.*
import kotlinx.android.synthetic.main.item_streams.view.*

class StreamsAdapter(
    private var isAnnouncementStream: Boolean,
    private val listener: (Stream) -> Unit
) : BaseAdapter<Stream>(R.layout.item_streams) {

    override fun bindViewHolder(holder: ViewHolder, data: Stream) {
        holder.itemView.apply {
            data.apply {

                setOnClickListener { listener.invoke(this) }
                img.loadImage(photo)
                name.text = title
                userDate.text = created_at.formatTime()
                // todo continue bind data
            }

            announcement.showGone(isAnnouncementStream)
            register.showGone(isAnnouncementStream)

            dur.showGone(!isAnnouncementStream)
            userDate.showGone(!isAnnouncementStream)

        }
    }
}