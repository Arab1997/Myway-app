package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder

class RepeatAdapter(private val listener: (Any) -> Unit) : BaseAdapter<Any>(R.layout.item_repeats) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            setOnClickListener {
                listener.invoke(data)
            }
        }
    }

}