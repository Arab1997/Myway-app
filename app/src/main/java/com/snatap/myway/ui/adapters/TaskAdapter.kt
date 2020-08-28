package com.snatap.myway.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.snatap.myway.R
import com.snatap.myway.network.models.Training
import com.snatap.myway.utils.extensions.inflate
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TimeLineViewHolder>() {

    private var data = arrayListOf<Training>()
    fun setData(data: ArrayList<Training>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder =
        TimeLineViewHolder(parent.inflate(R.layout.item_task), viewType)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        holder.itemView.apply {
            data[holder.adapterPosition].apply {
                img.loadImage(photo)
                name.text = title.capitalize()
                dur.text = duration
                setOnClickListener { listener.invoke(holder.adapterPosition) }
                if (finished) {
                    checked.setImageResource(R.drawable.ic_check)
                    timeline.marker = resources.getDrawable(R.drawable.oval_blue)
                } else {
                    checked.setImageResource(R.drawable.ic_task_play)
                    timeline.marker = resources.getDrawable(R.drawable.oval_hint)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    class TimeLineViewHolder(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        private var mTimelineView: TimelineView = itemView.findViewById(R.id.timeline)

        init {
            mTimelineView.initLine(viewType)
        }
    }

}