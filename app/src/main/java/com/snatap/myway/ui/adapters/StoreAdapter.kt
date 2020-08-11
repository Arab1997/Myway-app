package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Store
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.autoNotify
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.visible
import kotlinx.android.synthetic.main.content_store.view.*
import kotlinx.android.synthetic.main.item_store.view.*

class StoreAdapter(private val action: (Store) -> Unit, private val listener: (Store) -> Unit) :
    BaseAdapter<Store>(R.layout.item_store) {
    override fun setData(data: ArrayList<Store>) {
        autoNotify(items, data) { old, new ->
            old.id == new.id
                    && old.title == new.title
                    && old.count == new.count
                    && old.price == new.price
        }
        items = ArrayList(data)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Store) {
        holder.itemView.apply {
            data.apply {
                setOnClickListener { action.invoke(this) }

                if (photos.isNotEmpty()) image.loadImage(photos.first())
                name.text = title
                price_rub.text = price.toString()
                price_coin.text = coins.toString()
                amount.text = "$count шт"

                if (count != 0) {
                    buy.gone()
                    counter.visible()
                } else {
                    buy.visible()
                    counter.gone()
                }

                if (available) {
                    buy.setBackgroundResource(R.drawable.rounded_red_card)
                } else {
                    buy.setBackgroundResource(R.drawable.rounded_gray_card)
                    buy.text = "Недоступен"
                }

            }
        }
        handleClicks(holder, data)
    }

    @SuppressLint("SetTextI18n")
    private fun handleClicks(holder: ViewHolder, data: Store) {
        holder.itemView.apply {
            data.apply {
                buy.setOnClickListener {
                    if (data.available) {
                        count += 1
                        buy.gone()
                        amount.text = "$count шт"
                        counter.visible()
                        listener.invoke(this)
                    }
                }
                minus.setOnClickListener {
                    count -= 1
                    if (count == 0) {
                        buy.visible()
                        counter.gone()
                    }
                    listener.invoke(this)
                    amount.text = "$count шт"
                }
                plus.setOnClickListener {
                    count += 1
                    listener.invoke(this)
                    amount.text = "$count шт"
                }
            }
        }
    }
}