package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Tag
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_tag_icons.view.*

class TagIconsAdapter : BaseAdapter<Tag>(R.layout.item_tag_icons) {

    override fun bindViewHolder(holder: ViewHolder, data: Tag) {
        holder.itemView.apply {
            data.let { icon.loadImage(it.icon) }
        }
    }
}