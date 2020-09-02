package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.show
import com.snatap.myway.utils.extensions.showGone
import kotlinx.android.synthetic.main.item_task_images.view.*


data class ImageData(val path: String, val type: Types)

enum class Types { IMAGE, VIDEO, FILE }

class TaskImagesAdapter(
    private val showDelete: Boolean = false,
    private val listener: (delete: Boolean, data: ImageData) -> Unit
) : BaseAdapter<ImageData>(R.layout.item_task_images) {

    override fun bindViewHolder(holder: ViewHolder, data: ImageData) {
        holder.itemView.apply {
            data.apply {
                delete.showGone(showDelete)
                delete.setOnClickListener { listener.invoke(true, this) }
                container.setOnClickListener { listener.invoke(false, this) }

                when (type) {
                    Types.IMAGE -> image.show()
                    Types.VIDEO -> video.show()
                    Types.FILE -> {
                        image.gone()
                        file.show()
                    }
                }
                fileNameText.text = path
                image.loadImage(path)
            }
        }
    }
}
