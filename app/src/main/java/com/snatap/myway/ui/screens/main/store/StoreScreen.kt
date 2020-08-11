package com.snatap.myway.ui.screens.main.store

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.StoreCategories
import com.snatap.myway.ui.adapters.StoreAdapter
import com.snatap.myway.ui.adapters.StoreTagsAdapter
import com.snatap.myway.utils.extensions.showInvisible
import kotlinx.android.synthetic.main.screen_store.*

class StoreScreen : BaseFragment(R.layout.screen_store) {

    private lateinit var tagsAdapter: StoreTagsAdapter
    private lateinit var storeAdapter: StoreAdapter

    override fun initialize() {

        initViews()

        setClicks()
    }

    private fun initViews() {

        title.text = "Маркет MyWay"

        cart.setImageResource(R.drawable.ic_cart)

        swipeLayout.setOnRefreshListener {
            viewModel.getStoreItems()
            viewModel.getStoreCategories()
            viewModel.clearSharedProducts()
        }

        tagsAdapter = StoreTagsAdapter()

        storeAdapter = StoreAdapter({
            addFragment(StoreDetailScreen.newInstance(it))
        }, {
            viewModel.setSharedData(it, this::class.java.simpleName)
        })

        recyclerMenu.adapter = tagsAdapter

        recyclerItems.adapter = storeAdapter
    }

    private fun setClicks() {
        back.setOnClickListener { finishFragment() }

        cart.setOnClickListener { addFragment(AddCartScreen()) }
    }


    override fun observe() {
        viewModel.getStoreCategories()

        viewModel.stores.observe(viewLifecycleOwner, Observer {
            swipeLayout?.isRefreshing = false
            storeAdapter.setData(it)
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is StoreCategories) {
                tagsAdapter.setData(ArrayList(it.store_item_categories))
            }
        })

        viewModel.sharedStore.observe(viewLifecycleOwner, Observer {
            badge.text = it.size.toString()
            badge.showInvisible(it.isNotEmpty())
        })
    }

}