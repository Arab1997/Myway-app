package com.snatap.myway.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Comment
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.diffutils.CommentsDiffUtil
import com.snatap.myway.utils.extensions.formatTime
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.preferences.SharedManager
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(private val sharedManager: SharedManager) :
    BaseAdapter<Comment>(R.layout.item_comment) {

    override fun setData(data: ArrayList<Comment>) {
        val diff = DiffUtil.calculateDiff(CommentsDiffUtil(items, data))
        items.clear()
        items.addAll(data)
        diff.dispatchUpdatesTo(this)
    }

    override fun bindViewHolder(holder: ViewHolder, data: Comment) {
        holder.itemView.apply {
            data.apply {
                userImg.loadImage(user_avatar)
                name.text = user_name.capitalize()
                comment.text = text
                date.text = created_at.formatTime()

                container.setBackgroundResource(
                    if (user_id == sharedManager.user.id) R.drawable.rounded_red_card_transparent
                    else 0
                )
            }
        }
    }

}
