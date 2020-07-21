package com.snatap.myway.ui.screens.main.filter

import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class FilterTagsScreen : BaseFragment(R.layout.screen_recycler) {

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Тэги"

        recycler.adapter = Adapter {}.apply { setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) }
    }

    class Adapter(private val listener: (Any) -> Unit) : BaseAdapter(R.layout.item_tags_filter) {

        override fun bindViewHolder(holder: ViewHolder, data: Any) {

        }

    }
}