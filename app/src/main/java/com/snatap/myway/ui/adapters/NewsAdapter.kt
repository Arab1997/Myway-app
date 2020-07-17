package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder

class NewsAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter(R.layout.item_news) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            setOnClickListener { listener.invoke(data) }
        }
    }
}
