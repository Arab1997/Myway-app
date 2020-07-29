package com.snatap.myway.ui.screens.main.events

import androidx.recyclerview.widget.GridLayoutManager
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.GalleryAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class GalleryScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var adapter: GalleryAdapter

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Галерея"

        adapter = GalleryAdapter {
            inDevelopment(requireContext())
        }.apply { setData(ArrayList((1..20).toList())) }

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 3)
    }
}