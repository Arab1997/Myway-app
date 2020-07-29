package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.gone
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(private val listener: (Any) -> Unit) : BaseAdapter<Any>(R.layout.item_chat) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {

            removeLayout.gone() // todo

            setOnClickListener {
                listener.invoke(holder.adapterPosition)
            }

            removeLayout.setOnClickListener {
                items.remove(holder.adapterPosition)
                val dataNew = ArrayList<Any>()
                for (i in 1 until items.size)
                    if (i != holder.adapterPosition) dataNew.add(items[i])
                items = dataNew

                notifyDataSetChanged()
            }
        }
    }

}