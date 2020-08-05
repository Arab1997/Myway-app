package com.snatap.myway.ui.screens.main.home.media

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MediaContentAdapter
import kotlinx.android.synthetic.main.fragment_media_content.*

class MediaContentFragment : BaseFragment(R.layout.fragment_media_content) {

    override fun initialize() {
        recycler.adapter = MediaContentAdapter()
            .apply { setData(arrayListOf(1, 2, 3)) }

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }

}