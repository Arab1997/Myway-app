package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Event
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_past_events.view.*

class PastEventsAdapter(private val listener: (Event) -> Unit) :
    BaseAdapter<Event>(R.layout.item_past_events) {

    override fun bindViewHolder(holder: ViewHolder, data: Event) {
        holder.itemView.apply {
            data.apply {
                image.loadImage(photo)
                name.text = title

                val iconList = ArrayList(tags.filter { it.icon.isNotEmpty() })
                icons.adapter = TagIconsAdapter().apply { setData(iconList) }
                icons.showGone(iconList.isNotEmpty())

                setOnClickListener { listener.invoke(this) }
            }
        }
    }
}