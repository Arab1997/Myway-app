package com.snatap.myway.ui.screens.main.path

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Lesson
import com.snatap.myway.ui.adapters.ReportsAdapter
import com.snatap.myway.ui.adapters.TaskAdapter
import com.snatap.myway.ui.adapters.Types
import com.snatap.myway.ui.screens.main.home.media.MediaPlayerScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_simple_toolbar.*
import kotlinx.android.synthetic.main.content_task.*
import kotlinx.android.synthetic.main.fragment_task_detail.*
import kotlinx.android.synthetic.main.screen_lesson_detail.*

class LessonDetailScreen : BaseFragment(R.layout.screen_lesson_detail) {

    companion object {
        var lesson: Lesson? = null
        fun newInstance(lesson: Lesson): LessonDetailScreen {
            this.lesson = lesson
            return LessonDetailScreen()
        }
    }

    override fun initialize() {

        back.setOnClickListener { finishFragment() }

        lesson?.let {
            pager.adapter = TaskDetailPagerAdapter(
                it, arrayListOf("Описание урока", "Задание"), childFragmentManager
            )
            title.text = it.title
        }

        tabLayout.setupWithViewPager(pager)
    }
}

class TaskDetailFragment : BaseFragment(R.layout.fragment_task_detail) {

    companion object {
        fun newInstance(data: Lesson, showTasks: Boolean): TaskDetailFragment {
            return TaskDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("data", Gson().toJson(data))
                    putBoolean("showTasks", showTasks)
                }
            }
        }
    }

    private var data: Lesson? = null
    override fun initialize() {
        data = Gson().fromJson(requireArguments().getString("data"), Lesson::class.java)

        initCommonView()

        if (requireArguments().getBoolean("showTasks")) initTasks()
        else initMyTasks()
    }

    private fun initCommonView() {
        data?.let {
            name.text = it.title
            shortDesc.text = it.assignment_text.fromHtml()
            desc.text = it.text.fromHtml()
            image.loadImage(it.photo)
            it.video?.let { vid ->
                videoImage.loadImage(it.photo)
                videoContainer.show()
            }
            videoContainer.setOnClickListener { v ->
                addFragment(MediaPlayerScreen.newInstance(it.video!!, it.title))
            }
        }

        sendTask.setOnClickListener { addFragment(SendTaskScreen.newInstance(data!!.id)) }
    }

    private fun initTasks() {
        taskDetails.gone()
        sendTask.show()
        contentTask.show()

        data?.let { lesson ->
            start.setOnClickListener { openPlayer(lesson, 0) }

            lesson.training_items?.let {
                start.showGone(it.isNotEmpty())
                recyclerTask.adapter = TaskAdapter {
                    openPlayer(lesson, it)
                }.apply { setData(ArrayList(it)) }
            }
        }
    }

    private fun openPlayer(lesson: Lesson, currentTaskPos: Int) {
        val task = data?.training_items!![currentTaskPos]
        lesson.training_items!!.forEach { it.playing = false }
        val list = ArrayList(lesson.training_items!!)
        list[currentTaskPos] = list[currentTaskPos].apply { playing = true }
        addFragment(MediaPlayerScreen.newInstance(task.video, task.title, list))
    }

    private fun initMyTasks() {
        taskDetails.show()
        sendTask.gone()
        contentTask.gone()

        myTasks.setOnClickListener {
            addFragment(MyTasksScreen.newInstance(data!!))
        }

        recyclerMyTasks.adapter = ReportsAdapter(sharedManager.user.avatar) {
            when (it.type) {
                Types.IMAGE -> addFragment(ImageScreen.newInstance(it.path))
                Types.VIDEO -> addFragment(VideoScreen.newInstance(it.path))
                Types.FILE -> mainActivity.openPdf(it.path)
            }
        }.apply {
            data?.let { setData(ArrayList(it.lesson_reports)) }
        }
    }
}

class TaskDetailPagerAdapter(
    private val lesson: Lesson, private val data: ArrayList<String>, fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return TaskDetailFragment.newInstance(lesson, position != 0)
    }

    override fun getPageTitle(position: Int) = data[position]

    override fun getCount(): Int = data.size
}