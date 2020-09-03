package com.snatap.myway.ui.screens.main.events

import androidx.recyclerview.widget.GridLayoutManager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Gallery
import com.snatap.myway.ui.adapters.GalleryAdapter
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.ui.screens.main.common.VideoScreen
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class GalleryScreen : BaseFragment(R.layout.screen_recycler) {

    companion object {
        private var data = arrayListOf<Gallery>()
        fun newInstance(data: ArrayList<Gallery>): GalleryScreen {
            this.data = data
            return GalleryScreen()
        }
    }

    private lateinit var adapter: GalleryAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Галерея"

        adapter = GalleryAdapter(false) { showTotal, data ->
            if (data.photo != null) {
                addFragment(ImageScreen.newInstance(data.photo))
                return@GalleryAdapter
            }
            if (data.video != null) {
                addFragment(VideoScreen.newInstance(data.video))
                return@GalleryAdapter
            }
        }.apply { setData(data) }

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 3)

        swipeLayout.isEnabled = false
    }
}