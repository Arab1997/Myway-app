package com.snatap.myway.ui.screens.main.home.cart

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import kotlinx.android.synthetic.main.screen_product_details.*

class ProductDetailsScreen : BaseFragment(R.layout.screen_product_details) {

    override fun initialize() {
        pager.adapter = ProductPagerAdapter(arrayListOf(1, 2, 3), childFragmentManager)
    }

}

class ProductPagerScreen : BaseFragment(R.layout.screen_product_pager) {
    override fun initialize() {

    }

}

class ProductPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return ProductPagerScreen()
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }
}