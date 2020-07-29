package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_banner_podcast.view.*


class PodcastBannerAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter<Any>(R.layout.item_banner_podcast) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            when {
                holder.adapterPosition % 4 == 0 -> bannerBackground.setBackgroundResource(R.drawable.rounded_red_card)
                holder.adapterPosition % 4 == 1 -> bannerBackground.setBackgroundResource(R.drawable.rounded_blue_card)
                holder.adapterPosition % 4 == 2 -> bannerBackground.setBackgroundResource(R.drawable.rounded_orange_card)
                holder.adapterPosition % 4 == 3 -> bannerBackground.setBackgroundResource(R.drawable.rounded_violet_card)
            }
            setOnClickListener { listener.invoke(data) }
        }
    }

}