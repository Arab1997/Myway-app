package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder

class MyActiveAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter<Any>(R.layout.item_myevents) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.setOnClickListener {
            listener.invoke(data)
        }
    }
}