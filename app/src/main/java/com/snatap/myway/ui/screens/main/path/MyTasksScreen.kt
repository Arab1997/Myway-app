package com.snatap.myway.ui.screens.main.path

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_my_tasks.*

class MyTasksScreen : BaseFragment(R.layout.screen_my_tasks) {
    override fun initialize() {
        setClicks()

        initViews()

        recyclerReports.adapter = ReportsAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    private fun initViews() {
        back.invisible()

        title.text = "Мои задания"

        right.setImageResource(R.drawable.ic_close)

    }

    private fun setClicks() {
        right.setOnClickListener { finishFragment() }

        sendTaskBtn.setOnClickListener { addFragment(SendTaskScreen()) }
    }
}

class ReportsAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}