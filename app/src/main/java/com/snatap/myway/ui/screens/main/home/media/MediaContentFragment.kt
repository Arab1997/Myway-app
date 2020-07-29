package com.snatap.myway.ui.screens.main.home.media

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.fragment_media_content.*
import kotlinx.android.synthetic.main.item_content.view.*

class MediaContentFragment : BaseFragment(R.layout.fragment_media_content) {

    override fun initialize() {
        recycler.adapter = ContentAdapter()
            .apply { setData(arrayListOf(1, 2, 3)) }
    }

    class ContentAdapter : BaseAdapter<Any>(R.layout.item_content) {

        override fun bindViewHolder(holder: ViewHolder, data: Any) {
            holder.itemView.apply {
                setOnClickListener {
                    expandableLayout.toggle()

                    recyclerTask.adapter = TaskAdapter()
                        .apply { setData(arrayListOf(1, 2, 3, 4, 5)) }
                }
            }
        }
    }


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
}