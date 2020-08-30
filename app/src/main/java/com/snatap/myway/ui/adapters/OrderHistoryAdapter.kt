package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.OrderHistory
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime2
import kotlinx.android.synthetic.main.item_orders_history.view.*

class OrderHistoryAdapter : BaseAdapter<OrderHistory>(R.layout.item_orders_history) {

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: OrderHistory) {
        holder.itemView.apply {
            data.apply {

                items.adapter = OrderHistoryItemAdapter().apply {
                    setData(ArrayList(store_order_items))
                }
                orderId.text = "#$id"
                date.text = created_at.formatTime2()
                price.text = total_price.toString()
                // todo orderStatus.
            }
        }
    }

}