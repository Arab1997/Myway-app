package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_broadcast_filter.view.*
import kotlinx.android.synthetic.main.item_tags_filter.view.*

class BroadcastFilterItemAdapter : BaseAdapter<Any>(R.layout.item_broadcast_filter) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            tagIcon.setColorFilter(resources.getColor(R.color.white))
            tagText.setTextColor(resources.getColor(R.color.white))
            when {
                holder.adapterPosition % 4 == 0 -> {
                    tagIcon.setImageResource(R.drawable.ic_art)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_violet_card)
                    tagText.text = "Творчество"
                }
                holder.adapterPosition % 4 == 1 -> {
                    tagIcon.setImageResource(R.drawable.ic_cariers)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_orange_card)
                    tagText.text = "Карьера"
                }
                holder.adapterPosition % 4 == 2 -> {
                    tagIcon.setImageResource(R.drawable.ic_star)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_green_card)
                    tagText.text = "Здоровье"
                }
                holder.adapterPosition % 4 == 3 -> {
                    tagIcon.setImageResource(R.drawable.ic_cuboc)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_blue_card)
                    tagText.text = "Самора"
                }

            }
        }
    }
}