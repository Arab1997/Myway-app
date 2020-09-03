package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Event
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime4
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_past_events_fragment.view.*

class PastEventsFragmentAdapter(private val listener: (Event) -> Unit) :
    BaseAdapter<Event>(R.layout.item_past_events_fragment) {

    override fun bindViewHolder(holder: ViewHolder, data: Event) {
        holder.itemView.apply {
            data.apply {
                image.loadImage(photo)
                name.text = title
                date.text = start_date.formatTime4()

                setOnClickListener { listener.invoke(this) }
            }
        }
    }
}