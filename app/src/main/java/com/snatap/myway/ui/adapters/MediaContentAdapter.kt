package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_content.view.*


class MediaContentAdapter : BaseAdapter<Any>(R.layout.item_content) {

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