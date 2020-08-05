package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_my_task.view.*

class MyTaskAdapter : BaseAdapter<Any>(R.layout.item_my_task) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            data.apply {

                setOnClickListener {
                    expandableLayout.toggle()
                }

                recycler.adapter = TaskImagesAdapter()
                    .apply { setData(arrayListOf(1, 2, 3)) }
            }
        }
    }

}