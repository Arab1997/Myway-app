package com.snatap.myway.ui.screens.main.breakthrough

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.path.PathTasksScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import kotlinx.android.synthetic.main.fragment_breakthrough.*
import kotlinx.android.synthetic.main.fragment_past_path.*
import kotlinx.android.synthetic.main.fragment_past_path.participate
import kotlinx.android.synthetic.main.screen_path.*

class BreakthroughScreen: BaseFragment(R.layout.screen_breakthrough){

    override fun initialize() {
        pager.adapter =
            PathsPagerAdapter(arrayListOf(1, 2, 3), childFragmentManager)

        message.setOnClickListener { addFragment(ChatScreen()) }
        cart.setOnClickListener { addFragment(StoreScreen()) }
    }

}

class PathFragment: BaseFragment(R.layout.fragment_breakthrough){

    override fun initialize() {
        participate1.setOnClickListener { addFragment(VisualizationBreakthroughScreen()) }
    }
}

class PastPathFragment: BaseFragment(R.layout.fragment_past_breakthrough){

    override fun initialize() {
        participate1.setOnClickListener { addFragment(VisualizationBreakthroughScreen()) }
    }
}

class ThreePathFragment: BaseFragment(R.layout.fragment_three_breakthrough){

    override fun initialize() {
        participate1.setOnClickListener { addFragment(VisualizationBreakthroughScreen()) }
    }
}


class PathsPagerAdapter(private val data: ArrayList<Any>, fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return if (position == 0) PathFragment()
        else  if (position == 1) PastPathFragment()
        else ThreePathFragment()
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try { super.restoreState(state, loader) } catch (e: Exception) { }
    }
}
