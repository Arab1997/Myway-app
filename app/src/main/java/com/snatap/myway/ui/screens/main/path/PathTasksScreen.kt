package com.snatap.myway.ui.screens.main.path

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Season
import com.snatap.myway.network.models.SeasonResp
import com.snatap.myway.ui.adapters.PathTasksScreenAdapter
import com.snatap.myway.utils.extensions.invisible
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_path_tasks.*

class PathTasksScreen : BaseFragment(R.layout.screen_path_tasks) {

    companion object {
        private var season: Season? = null
        fun newInstance(season: Season): PathTasksScreen {
            this.season = season
            return PathTasksScreen()
        }
    }

    private lateinit var adapter: PathTasksScreenAdapter
    override fun initialize() {

        back.invisible()

        title.text = "Базовый курс"

        season?.let { root.loadImage(it.photo) }
        adapter = PathTasksScreenAdapter { data, action ->
            addFragment(
                if (action) SendTaskScreen.newInstance(data.id)
                else LessonDetailScreen.newInstance(data)
            )
        }
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            season?.let { viewModel.getLessonSeason(it.id) }
        }
    }

    override fun observe() {
        viewModel.apply {
            season?.let { getLessonSeason(it.id) }
            data.observe(viewLifecycleOwner, Observer {
                if (it is SeasonResp) {
                    swipeLayout?.isRefreshing = false
                    it.lesson_season.lesson_items?.let { lessons ->
                        adapter.setData(ArrayList(lessons.sortedByDescending { it.pinned }))
                    }
                }
            })
        }
    }
}