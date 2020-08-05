package com.snatap.myway.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.snatap.myway.R
import com.snatap.myway.utils.extensions.inflate

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TimeLineViewHolder>() {

    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder =
        TimeLineViewHolder(parent.inflate(R.layout.item_task), viewType)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        holder.itemView.apply {
            data[holder.adapterPosition].apply {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    inner class TimeLineViewHolder(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        private var mTimelineView: TimelineView = itemView.findViewById(R.id.timeline)

        init {
            mTimelineView.initLine(viewType)
        }
    }

}