package com.snatap.myway.ui.adapters

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.item_live_comment_file.view.*

class LiveCommentAdapter(isTransparent: Boolean = false) : RecyclerView.Adapter<ViewHolder>() {

    private var isTransparent: Boolean = isTransparent

    private var data = arrayListOf<Any>()

    public fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        parent.inflate(
            when(viewType){
                Constants.COMMENT_WITH_FILE -> R.layout.item_live_comment_file
                else -> R.layout.item_live_comment
            },
            false
        )
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isTransparent){
            if (position % 5 == 0) {
                holder.itemView.liveCommentFileBg.setBackgroundResource(0)
                holder.itemView.fileName.setTextColor(Color.parseColor("#FFFFFF"))
            }
            holder.itemView.name.setTextColor(Color.parseColor("#FFFFFF"))
            holder.itemView.comment.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 5 == 0 -> Constants.COMMENT_WITH_FILE
            else -> Constants.COMMENT_MSG
        }
    }

}