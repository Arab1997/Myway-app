package com.snatap.myway.ui.adapters

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.utils.common.ViewHolder

class MediaPlayerAdapter(private val listener: (Any) -> Unit) :
    BaseAdapter<Any>(R.layout.item_media_player) {

    override fun bindViewHolder(holder: ViewHolder, data: Any) {

    }
}