package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Store
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_orders_history_items.view.*

class OrderHistoryItemAdapter : BaseAdapter<Store>(R.layout.item_orders_history_items) {

    override fun bindViewHolder(holder: ViewHolder, data: Store) {
        holder.itemView.apply {
            data.apply {

                if (photos.isNotEmpty()) image.loadImage(photos.first())
                name.text = title
                priceRub.text = price.toString()
                // todo set description
            }
        }
    }

}