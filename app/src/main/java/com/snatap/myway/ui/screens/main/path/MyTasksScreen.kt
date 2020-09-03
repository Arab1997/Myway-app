package com.snatap.myway.ui.screens.main.path

import android.os.Bundle
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Lesson
import com.snatap.myway.ui.adapters.ReportsAdapter
import com.snatap.myway.ui.adapters.Types
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.ui.screens.main.common.VideoScreen
import com.snatap.myway.utils.extensions.invisible
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_my_tasks.*

class MyTasksScreen : BaseFragment(R.layout.screen_my_tasks) {

    companion object {
        fun newInstance(data: Lesson): MyTasksScreen {
            return MyTasksScreen().apply {
                arguments = Bundle().apply {
                    putString("data", Gson().toJson(data))
                }
            }
        }
    }

    private lateinit var data: Lesson
    override fun initialize() {

        data = Gson().fromJson(requireArguments().getString("data"), Lesson::class.java)

        setClicks()

        initViews()
    }

    private fun initViews() {
        back.invisible()

        title.text = "Мои задания"

        right.setImageResource(R.drawable.ic_close)

        recycler.adapter = ReportsAdapter(sharedManager.user.avatar) {
            when (it.type) {
                Types.IMAGE -> addFragment(ImageScreen.newInstance(it.path))
                Types.VIDEO -> addFragment(VideoScreen.newInstance(it.path))
                Types.FILE -> mainActivity.openPdf(it.path)
            }
        }.apply {
            setData(ArrayList(data.lesson_reports))
        }

        swipeLayout.isEnabled = false
    }

    private fun setClicks() {
        right.setOnClickListener { finishFragment() }

        sendTaskBtn.setOnClickListener { addFragment(SendTaskScreen.newInstance(data.id)) }
    }
}
