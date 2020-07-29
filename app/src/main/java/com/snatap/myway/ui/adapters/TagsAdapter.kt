package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Tag
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_tags.view.*

class TagsAdapter : BaseAdapter<Tag>(R.layout.item_tags) {

    override fun bindViewHolder(holder: ViewHolder, data: Tag) {
        holder.itemView.apply {
            data.apply {
                tagName.text = title
            }
        }
    }
}