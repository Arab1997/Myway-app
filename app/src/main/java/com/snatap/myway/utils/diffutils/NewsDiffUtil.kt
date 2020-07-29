package com.snatap.myway.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.snatap.myway.network.models.News

class NewsDiffUtil(
    private var oldList: List<News>,
    private var newList: List<News>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(p0: Int, p1: Int) = oldList[p0].id == newList[p1].id
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areContentsTheSame(p0: Int, p1: Int) =
        oldList[p0].is_bookmarked == newList[p1].is_bookmarked &&
                oldList[p0].is_liked == newList[p1].is_liked
}
