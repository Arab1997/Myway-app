package com.snatap.myway.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_banner_podcast.view.*


class PodcastChapterAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_podcast_chapter, parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}