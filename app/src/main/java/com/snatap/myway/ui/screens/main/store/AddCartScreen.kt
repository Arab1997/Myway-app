package com.snatap.myway.ui.screens.main.store

import android.app.AlertDialog
import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Store
import com.snatap.myway.ui.adapters.AddCartAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_add_cart.*

class AddCartScreen : BaseFragment(R.layout.screen_add_cart) {

    private lateinit var adapter: AddCartAdapter
    override fun initialize() {

        setViews()

        setClicks()
    }

    private fun setClicks() {

        right.setOnClickListener { finishFragment() }

        payRub.setOnClickListener { inDevelopment(requireContext()) }

        payCoin.setOnClickListener { inDevelopment(requireContext()) }
    }

    private fun setViews() {
        back.invisible()

        right.setImageResource(R.drawable.ic_close)

        title.text = "Корзина"

        adapter = AddCartAdapter({ data, delete ->
            if (delete) delete(data)
            else addFragment(StoreDetailScreen.newInstance(data))
        }, {
            viewModel.setSharedData(it, this::class.java.simpleName)
        })
        recycler.adapter = adapter
    }

    private fun delete(data: Store) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить")
            .setMessage("Вы точно хотите удалить из козины?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.setSharedData(data.copy(count = 0), this::class.java.simpleName)
            }
            .setNegativeButton("Нет") { _, _ ->
            }.show()
    }

    private var totalCoin = 0
    private var totalRub = 0
    override fun observe() {
        viewModel.sharedStore.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)

            totalCoin = 0
            totalRub = 0

            it.forEach {
                totalCoin += it.count * it.coins
                totalRub += it.count * it.price
            }
            coin.text = totalCoin.toString()
            rub.text = totalRub.toString()

        })
    }

}