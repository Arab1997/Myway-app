package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Tag
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_tags_filter.view.*

class FilterTagsAdapter(private val listener: (Int) -> Unit) :
    BaseAdapter<Tag>(R.layout.item_tags_filter) {

    override fun bindViewHolder(holder: ViewHolder, data: Tag) {
        holder.itemView.apply {
            data.apply {
                tagText.text = title
                tagIcon.loadImage(icon)
                backgroundColor.setBackgroundResource(if (isChecked) color else 0)
                setOnClickListener { listener.invoke(holder.adapterPosition) }
            }
        }
    }

}