package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Tag
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_tags_filter.view.*
import kotlin.random.Random


class FilterTagsAdapter(private val listener: (Int) -> Unit) :
    BaseAdapter<Tag>(R.layout.item_tags_filter) {

    private var color = 0
    override fun bindViewHolder(holder: ViewHolder, data: Tag) {
        holder.itemView.apply {
            data.apply {
                tagText.text = title
                tagIcon.loadImage(icon)

                color =
                    if (isChecked) Constants.colors[Random.nextInt(0, 100) % Constants.colors.size]
                    else 0
                backgroundColor.setBackgroundResource(color)
                setOnClickListener { listener.invoke(holder.adapterPosition) }
            }
        }
    }

}