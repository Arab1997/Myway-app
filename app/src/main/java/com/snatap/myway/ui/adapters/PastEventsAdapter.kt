package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder


class PastEventsAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter<Any>(R.layout.item_past_events) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            data.apply {
/*
                    image.loadImage(img)
                    name.text = title
                    date.text = desc
*/

                setOnClickListener { listener.invoke(this) }
            }
        }
    }
}