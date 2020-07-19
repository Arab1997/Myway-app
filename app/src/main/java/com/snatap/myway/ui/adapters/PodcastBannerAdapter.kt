package com.snatap.myway.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_banner_podcast.view.*


class PodcastBannerAdapter(private val listener: (Any) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_banner_podcast, parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            when {
                position % 4 == 0 -> bannerBackground.setBackgroundResource(R.drawable.rounded_red_card)
                position % 4 == 1 -> bannerBackground.setBackgroundResource(R.drawable.rounded_blue_card)
                position % 4 == 2 -> bannerBackground.setBackgroundResource(R.drawable.rounded_orange_card)
                position % 4 == 3 -> bannerBackground.setBackgroundResource(R.drawable.rounded_violet_card)
            }
        }
        holder.itemView.setOnClickListener { listener.invoke(position) }
    }

}