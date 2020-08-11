package com.snatap.myway.ui.screens.main.store

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Store
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.screen_product_pager.*
import kotlinx.android.synthetic.main.screen_store_details.*

class StoreDetailScreen : BaseFragment(R.layout.screen_store_details) {

    companion object {
        private var data: Store? = null
        fun newInstance(data: Store): StoreDetailScreen {
            this.data = data
            return StoreDetailScreen()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initialize() {

        close.setOnClickListener { finishFragment() }

        data?.apply {
            pager.adapter = StoreDetailPagerAdapter(photos, childFragmentManager)
            imgCount.text = "${photos.size} шт"

            name.text = title
            price_rub.text = price.toString()
            price_coin.text = coins.toString()
            amount.text = "$count шт"
            desc.text = description

            if (available) {
                buy.setBackgroundResource(R.drawable.rounded_red_card)
            } else {
                buy.setBackgroundResource(R.drawable.rounded_gray_card)
                buy.text = "Недоступен"
            }
            buy.setOnClickListener {
                if (available) {
                    count += 1
                    amount.text = "$count шт"
                    viewModel.setSharedData(this,this::class.java.simpleName)
                }
            }
            minus.setOnClickListener {
                if (available) {
                    count -= 1
                    amount.text = "$count шт"
                }
            }
            plus.setOnClickListener {
                if (available) {
                    count += 1
                    amount.text = "$count шт"
                }
            }
        }
    }

}

class StorePagerFragment : BaseFragment(R.layout.screen_product_pager) {
    companion object {
        fun newInstance(url: String): StorePagerFragment {
            return StorePagerFragment().apply {
                arguments = Bundle().apply {
                    putString("url", url)
                }
            }
        }
    }

    override fun initialize() {
        arguments?.let {
            pagerImg.loadImage(it.getString("url", ""))
        }
    }

}

class StoreDetailPagerAdapter(private val data: List<String>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = StorePagerFragment.newInstance(data[position])

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }
}