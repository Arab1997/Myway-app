package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Store
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.autoNotify
import com.snatap.myway.utils.extensions.gone
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.visible
import com.snatap.myway.utils.views.SwipeLayout
import kotlinx.android.synthetic.main.content_store.view.*
import kotlinx.android.synthetic.main.item_add_cart.view.*

class AddCartAdapter(
    private val action: (Store, Boolean) -> Unit, private val listener: (Store) -> Unit
) : BaseAdapter<Store>(R.layout.item_add_cart) {

    private var itemsOffset = IntArray(0)
    override fun setData(data: ArrayList<Store>) {
        autoNotify(items, data) { old, new ->
            old.id == new.id
                    && old.title == new.title
                    && old.count == new.count
                    && old.price == new.price
        }
        items = ArrayList(data)
        itemsOffset = IntArray(data.size)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Store) {
        holder.itemView.apply {
            data.apply {
                storeContainer.setOnClickListener { action.invoke(this, false) }

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

        bindSwipe(holder, holder.adapterPosition)

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

    private fun bindSwipe(holder: ViewHolder, position: Int) {
        holder.itemView.swipeLayout.apply {
            offset = itemsOffset[position]
            setOnSwipeListener(object : SwipeLayout.OnSwipeListener {
                override fun onBeginSwipe(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }

                override fun onSwipeClampReached(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                    action.invoke(items[holder.adapterPosition], true)
                    Handler().postDelayed({ swipeLayout.animateReset() }, 500)
                }

                override fun onLeftStickyEdge(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }

                override fun onRightStickyEdge(
                    swipeLayout: SwipeLayout,
                    moveToRight: Boolean
                ) {
                }
            })
        }

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            itemsOffset[holder.adapterPosition] = holder.itemView.swipeLayout.offset
        }
    }
}