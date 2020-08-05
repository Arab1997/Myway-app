package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_gallery.view.*


class GalleryAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter<Any>(R.layout.item_gallery) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            data.apply {

                totalCount.showGone(holder.adapterPosition == items.lastIndex)

                setOnClickListener { listener.invoke(this) }
            }
        }
    }
}
