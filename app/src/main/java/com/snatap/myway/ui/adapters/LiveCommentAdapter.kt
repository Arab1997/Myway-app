package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.setTextColorRes
import kotlinx.android.synthetic.main.item_live_comment.view.*

class LiveCommentAdapter(private val whiteColor: Boolean) :
    BaseAdapter<Any>(R.layout.item_live_comment) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {
        holder.itemView.apply {
            if (whiteColor) {
                name.setTextColorRes(R.color.white)
                comment.setTextColorRes(R.color.white)
            }
        }
    }
}