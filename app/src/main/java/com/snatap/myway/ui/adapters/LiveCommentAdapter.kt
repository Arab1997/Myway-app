package com.snatap.myway.ui.adapters

import android.graphics.Color
import android.view.ViewGroup
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.item_live_comment_file.view.*

class LiveCommentAdapter(val isTransparent: Boolean = false) :
    BaseAdapter<Any>(R.layout.item_live_comment) {

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (isTransparent) {
//            if (position % 5 == 0) {
//                holder.itemView.liveCommentFileBg.setBackgroundResource(0)
//                holder.itemView.fileName.setTextColor(Color.parseColor("#FFFFFF"))
//            }
//            holder.itemView.name.setTextColor(Color.parseColor("#FFFFFF"))
//            holder.itemView.comment.setTextColor(Color.parseColor("#FFFFFF"))
//        }
//    }

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        if (isTransparent) {
            holder.itemView.name.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.comment.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }


}