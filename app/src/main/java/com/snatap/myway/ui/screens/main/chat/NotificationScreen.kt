package com.snatap.myway.ui.screens.main.chat

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.NotificationAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class NotificationScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var adapter: NotificationAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "Уведомления"

        adapter = NotificationAdapter()
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            viewModel.getUserNotifications()
        }
    }

    override fun observe() {
        viewModel.notifications.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            adapter.setData(it)
        })
    }

}