package com.snatap.myway.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_gallery.view.*


class GalleryAdapter(private val listener: (Any) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var data = listOf<Any>()
    fun setData(data: List<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            data[holder.adapterPosition].apply {

                totalCount.showGone(holder.adapterPosition == data.lastIndex)

                setOnClickListener { listener.invoke(this) }
            }
        }
    }
}
