package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Notification
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime3
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter : BaseAdapter<Notification>(R.layout.item_notification) {

    override fun bindViewHolder(holder: ViewHolder, data: Notification) {
        holder.itemView.apply {
            data.apply {
                name.text = title
                desc.text = description
                time.text = created_at.formatTime3()
            }
        }
    }

}