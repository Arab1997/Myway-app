package com.snatap.myway.ui.adapters

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Lesson
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime
import com.snatap.myway.utils.extensions.formatTime4
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.views.SwipeLayout
import kotlinx.android.synthetic.main.item_path_task.view.*

class PathTasksScreenAdapter(private val listener: (Lesson, Boolean) -> Unit) :
    BaseAdapter<Lesson>(R.layout.item_path_task) {

    private var itemsOffset = IntArray(0)
    override fun setData(data: ArrayList<Lesson>) {
        super.setData(data)
        itemsOffset = IntArray(data.size)
    }

    override fun bindViewHolder(holder: ViewHolder, data: Lesson) {
        holder.itemView.apply {
            data.apply {

                dataBlock.setOnClickListener {
                    listener.invoke(this, false)
                }
                date.text = datetime.formatTime4()
                name.text = title
                deadline.text =
                    resources.getString(R.string.deadline_s, report_deadline_at.formatTime())
                pin.showGone(pinned)

                /* todo lesson status
                * status.text
                * dataBlock.setBackgroundResource(R.drawable.rounded_blue_bordered_card)
                *  dataBlock.setBackgroundResource(R.drawable.rounded_green_bordered_card)
                *  dataBlock.setBackgroundResource(R.drawable.rounded_red_bordered_card)
                * */
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