package com.snatap.myway.ui.screens.main.path

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.PathTasksScreenAdapter
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.screen_path_tasks.*

class PathTasksScreen : BaseFragment(R.layout.screen_path_tasks){
    override fun initialize() {
        recycler.adapter = PathTasksScreenAdapter(arrayListOf(1,2,3,4,5,6,7))
    }
}