package com.snatap.myway.ui.adapters

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.views.SwipeLayout
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(private val listener: (Any, Boolean) -> Unit) :
    BaseAdapter<Any>(R.layout.item_chat) {

    private var itemsOffset = IntArray(0)
    override fun setData(data: ArrayList<Any>) {
        super.setData(data)
        itemsOffset = IntArray(data.size)
    }

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            dataBlock.setOnClickListener {
                listener.invoke(holder.adapterPosition, false)
            }
        }

        bindSwipe(holder, holder.adapterPosition)
    }

    private fun bindSwipe(holder: ViewHolder, position: Int) {
        holder.itemView.swipeLayout.apply {
            offset = itemsOffset[position]
            setOnSwipeListener(object : SwipeLayout.OnSwipeListener {
                override fun onBeginSwipe(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }

                override fun onSwipeClampReached(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                    listener.invoke(items[holder.adapterPosition], true)
                    Handler().postDelayed({ swipeLayout.animateReset() }, 500)
                }

                override fun onLeftStickyEdge(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }

                override fun onRightStickyEdge(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }
            })
        }

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            itemsOffset[holder.adapterPosition] = holder.itemView.swipeLayout.offset
        }
    }
}