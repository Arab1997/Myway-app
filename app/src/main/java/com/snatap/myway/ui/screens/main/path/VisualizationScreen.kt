package com.snatap.myway.ui.screens.main.path

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MyTaskAdapter
import kotlinx.android.synthetic.main.content_simple_toolbar.*
import kotlinx.android.synthetic.main.fragement_task_detail.*
import kotlinx.android.synthetic.main.screen_visualization.*

class VisualizationScreen : BaseFragment(R.layout.screen_visualization) {

    override fun initialize() {
        title.text = "Визуализация"

        pager.adapter = VisualizationPagerAdapter(
            arrayListOf("Описание урока", "Задание"),
            childFragmentManager
        )
        tabLayout.setupWithViewPager(pager)
    }

}

class TaskDetailFragment : BaseFragment(R.layout.fragement_task_detail) {
    override fun initialize() {

        myTasks.setOnClickListener {
            addFragment(MyTasksScreen())
        }

        recycler.adapter = MyTaskAdapter().apply {
            setData(arrayListOf(1, 2, 3))
        }
    }
}

class TasksFragment : BaseFragment(R.layout.fragment_task) {
    override fun initialize() {
/*
        recyclerTask.adapter = TaskAdapter()
            .apply { setData(arrayListOf(1, 2, 3, 4, 5)) }*/ // todo
    }

}

class VisualizationPagerAdapter(private val data: ArrayList<String>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) TaskDetailFragment()
        else TasksFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position]
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