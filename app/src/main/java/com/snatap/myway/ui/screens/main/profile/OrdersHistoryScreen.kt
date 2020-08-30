package com.snatap.myway.ui.screens.main.profile

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.OrderHistoryResp
import com.snatap.myway.ui.adapters.OrderHistoryAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class OrdersHistoryScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var adapter: OrderHistoryAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        title.text = "История покупок"

        adapter = OrderHistoryAdapter()
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            viewModel.getUserOrdersHistory()
        }
    }

    override fun observe() {
        viewModel.apply {

            getUserOrdersHistory()

            data.observe(viewLifecycleOwner, Observer {
                if (it is OrderHistoryResp) {
                    swipeLayout.isRefreshing = false
                    adapter.setData(ArrayList(it.store_orders))
                }
            })
        }
    }
}