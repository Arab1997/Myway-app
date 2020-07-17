package com.snatap.myway.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate


abstract class BaseAdapter(@LayoutRes val layoutID: Int) : RecyclerView.Adapter<ViewHolder>() {

    protected var items = arrayListOf<Any>()

    open fun setData(data: ArrayList<Any>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(layoutID))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindViewHolder(this, items[holder.adapterPosition])
        }
    }

    abstract fun bindViewHolder(holder: ViewHolder, data: Any)

}