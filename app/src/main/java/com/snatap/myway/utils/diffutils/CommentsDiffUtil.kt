package com.snatap.myway.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.snatap.myway.network.models.Comment

class CommentsDiffUtil(
    private var oldList: List<Comment>,
    private var newList: List<Comment>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(p0: Int, p1: Int) = oldList[p0].id == newList[p1].id
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areContentsTheSame(p0: Int, p1: Int) =
        oldList[p0].user_id == newList[p1].user_id &&
                oldList[p0].text == newList[p1].text
}
