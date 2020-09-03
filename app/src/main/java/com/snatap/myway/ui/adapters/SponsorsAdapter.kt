package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Sponsor
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_sponsors.view.*

class SponsorsAdapter : BaseAdapter<Sponsor>(R.layout.item_sponsors) {
    override fun bindViewHolder(holder: ViewHolder, data: Sponsor) {
        holder.itemView.apply {
            image.loadImage(data.photo)
        }
    }
}
