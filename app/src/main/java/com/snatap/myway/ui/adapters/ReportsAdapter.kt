package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.LessonReport
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.formatTime
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_reports.view.*

class ReportsAdapter(
    private val userAvatar: String,
    private val openListener: (ImageData) -> Unit
) : BaseAdapter<LessonReport>(R.layout.item_reports) {

    override fun bindViewHolder(holder: ViewHolder, data: LessonReport) {
        holder.itemView.apply {
            data.apply {

                setOnClickListener {
                    expandableLayout.toggle()
                }
//                reportStatus.text  // todo
                content.text = text
                userImg.loadImage(userAvatar)
                time.text = created_at.formatTime()

                recycler.adapter = TaskImagesAdapter(false) { _, data ->
                    openListener.invoke(data)
                }.apply {
                    val list = ArrayList(items.map {
                        var path = ""
                        var type = Types.IMAGE
                        when {
                            it.photo != null -> {
                                path = it.photo
                                type = Types.IMAGE
                            }
                            it.video != null -> {
                                path = it.video
                                type = Types.VIDEO
                            }
                            it.file != null -> {
                                path = it.file
                                type = Types.FILE
                            }
                        }
                        ImageData(path, type)
                    })
                    setData(list)
                }
            }
        }
    }

}