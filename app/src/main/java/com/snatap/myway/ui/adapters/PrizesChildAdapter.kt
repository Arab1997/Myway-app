package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Notification
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime3
import kotlinx.android.synthetic.main.fragement_vizual_break_detail.view.*
import kotlinx.android.synthetic.main.item_my_task.view.*
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.android.synthetic.main.screen_break_visual.view.*

class PrizesChildAdapter (private val listener: (Any) -> Unit) : BaseAdapter<Any>(R.layout.item_champs_fragment) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.setOnClickListener {
            listener.invoke(data)
        }
    }

}