package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.News
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.blockClickable
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_news_similar.view.*

class SimilarNewsAdapter(private val listener: (News) -> Unit) :
    BaseAdapter<News>(R.layout.item_news_similar) {

    override fun bindViewHolder(holder: ViewHolder, data: News) {
        holder.itemView.apply {
            data.apply {

                image.loadImage(photo)
                name.text = title

                setOnClickListener {
                    it.blockClickable()
                    listener.invoke(data)
                }
            }
        }
    }
}