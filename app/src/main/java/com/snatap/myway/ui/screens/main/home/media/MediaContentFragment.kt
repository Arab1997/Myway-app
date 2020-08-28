package com.snatap.myway.ui.screens.main.home.media

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.MediaContentAdapter
import kotlinx.android.synthetic.main.fragment_media_content.*

class MediaContentFragment : BaseFragment(R.layout.fragment_media_content) {

    private lateinit var adapter: MediaContentAdapter

    override fun initialize() {
        adapter = MediaContentAdapter({ lesson, currentTaskPos ->
            val task = lesson.training_items!![currentTaskPos]
            lesson.training_items!!.forEach { it.playing = false }
            val list = ArrayList(lesson.training_items!!)
            list[currentTaskPos] = list[currentTaskPos].apply { playing = true }
            addFragment(MediaPlayerScreen.newInstance(task.video, task.title, list))
        }, {
            addFragment(MediaPlayerScreen.newInstance(it.video!!, it.title))
        })
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            viewModel.getLessonsDay()
        }
    }

    override fun observe() {
        viewModel.lessonsDay.observe(viewLifecycleOwner, Observer {
            swipeLayout.isRefreshing = false
            sharedManager.finishedTasks.forEach { id ->
                it.forEachIndexed { i, lessonDay ->
                    if (!lessonDay.training_items.isNullOrEmpty()) {
                        val tasks = ArrayList(lessonDay.training_items!!)
                        tasks.forEachIndexed { index, task ->
                            if (task.id == id) tasks[index] = tasks[index].apply { finished = true }
                        }
                        lessonDay.training_items = tasks
                    }
                }

            }
            adapter.setData(it)
        })
    }
}