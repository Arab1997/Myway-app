package com.snatap.myway.ui.screens.main.breakthrough

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Lesson
import com.snatap.myway.ui.adapters.ParticipantAdapter
import com.snatap.myway.ui.adapters.ReportsAdapter
import com.snatap.myway.ui.adapters.TaskAdapter
import com.snatap.myway.ui.adapters.Types
import com.snatap.myway.ui.screens.main.HomeData
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.ui.screens.main.common.VideoScreen
import com.snatap.myway.ui.screens.main.home.media.MediaPlayerScreen
import com.snatap.myway.ui.screens.main.path.LessonDetailScreen.Companion.lesson
import com.snatap.myway.ui.screens.main.path.MyTasksScreen
import com.snatap.myway.ui.screens.main.path.SendTaskScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.content_task.*
import kotlinx.android.synthetic.main.fragment_task_detail.*
import kotlinx.android.synthetic.main.screen_rating_detail.*

class RatingDetailScreen : BaseFragment(R.layout.screen_rating_detail) {
    private var data = arrayListOf<HomeData>()

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): ChampsDetailsScreen {
            Companion.txtTitle = txtTitle
            return ChampsDetailsScreen()
        }
    }


    override fun initialize() {

      // pagerRating.adapter = RatingPagerAdapter(arrayListOf("Общий", "Друзья"), childFragmentManager)

        lesson?.let {
            pagerRating.adapter = TaskDetailPagerAdapter(
                it, arrayListOf("Общий", "Друзья"), childFragmentManager
            )
            title.text = it.title
        }

        tabLayoutRating.setupWithViewPager(pagerRating)

        tabLayoutRating.getTabAt(0)!!.setIcon(data[0].icon)
        tabLayoutRating.getTabAt(1)!!.setIcon(data[1].icon)

        initViews()
    }

    private fun initViews() {
        recyclerParticipants.adapter = ParticipantAdapter {
            addFragment(WheelFortuneScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }


      /*  recyclerAllFriendd.adapter = WheelAllFriendsAdapter {
            addFragment(WheelFortuneAllFriendsScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }*/
    }
}



class TaskDetailPagerAdapter(
    private val lesson: Lesson, private val data: ArrayList<String>, fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
       // return TaskDetailFragment.newInstance(lesson, position != 0)

       /* return if (position != 0) TaskDetailFragment.newInstance(lesson, position == 0)
        else SecondFragment()*/


        return if (position == 0) FirstFragment()
        else SecondFragment()
    }

    override fun getPageTitle(position: Int) = data[position]

    override fun getCount(): Int = data.size
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


class RatingPagerAdapter(
    private val data: ArrayList<Any>, fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return if (position == 0) FirstFragment()
        else SecondFragment()
    }

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try { super.restoreState(state, loader) } catch (e: Exception) { }
    }
}
class FirstFragment: BaseFragment(R.layout.fragment_all_friends){

    override fun initialize() {
       // recyclerAllFriend.setOnClickListener { addFragment(WheelFortuneAllFriendsScreen()) }
    }
}
class SecondFragment: BaseFragment(R.layout.fragment_my_friends){
    override fun initialize() {
       // recyclerMyFriend.setOnClickListener { addFragment(WheelFortuneMyFriendsScreen()) }
    }
}

