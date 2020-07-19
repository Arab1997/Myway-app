package com.snatap.myway.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_broadcast_filter.view.*
import kotlinx.android.synthetic.main.item_tags_filter.view.*

class BroadcastFilterItemAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_broadcast_filter, parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply{
            tagIcon.setColorFilter(resources.getColor(R.color.white))
            tagText.setTextColor(resources.getColor(R.color.white))
            when{
                position % 4 == 0 -> {
                    tagIcon.setImageResource(R.drawable.ic_art)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_violet_card)
                    tagText.text = "Творчество"
                }
                position % 4 == 1 -> {
                    tagIcon.setImageResource(R.drawable.ic_cariers)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_orange_card)
                    tagText.text = "Карьера"
                }
                position % 4 == 2 -> {
                    tagIcon.setImageResource(R.drawable.ic_star)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_green_card)
                    tagText.text = "Здоровье"
                }
                position % 4 == 3 -> {
                    tagIcon.setImageResource(R.drawable.ic_cuboc)
                    itemFilterBackground.setBackgroundResource(R.drawable.rounded_blue_card)
                    tagText.text = "Самора"
                }

            }
        }
    }
}