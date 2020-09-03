package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Gallery
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.show
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter(
    private val showTotalText: Boolean,
    private val listener: (showTotal: Boolean, data: Gallery) -> Unit
) : BaseAdapter<Gallery>(R.layout.item_gallery) {

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Gallery) {
        holder.itemView.apply {
            data.apply {

                photo?.let { image.loadImage(it) }
                video?.let { videoImg.show() }
                totalCount.showGone(showTotalText && items.size > 6 && holder.adapterPosition == items.lastIndex)
                totalCount.text = "+${items.size - 6}"

                image.setOnClickListener { listener.invoke(false, this) }
                videoImg.setOnClickListener { listener.invoke(false, this) }

                totalCount.setOnClickListener { listener.invoke(true, this) }
            }
        }
    }
}
