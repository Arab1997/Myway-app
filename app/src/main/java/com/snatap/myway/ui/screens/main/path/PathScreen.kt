package com.snatap.myway.ui.screens.main.path

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Season
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_events_toolbar.*
import kotlinx.android.synthetic.main.fragment_path.*
import kotlinx.android.synthetic.main.screen_path.*

class PathScreen : BaseFragment(R.layout.screen_path) {

    private lateinit var adapter: PathsPagerAdapter
    override fun initialize() {

        back.gone()

        rightExtra.setOnClickListener { addFragment(ChatScreen()) }

        right.setOnClickListener { addFragment(StoreScreen()) }

        adapter = PathsPagerAdapter(childFragmentManager)
        pager.adapter = adapter
    }

    override fun observe() {
        viewModel.lessonSeasons.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            pager_indicator_register.setViewPager(pager)
        })
    }

}

class PathFragment : BaseFragment(R.layout.fragment_path) {

    companion object {
        fun newInstance(data: Season): PathFragment {
            return PathFragment().apply {
                arguments = Bundle().apply {
                    putString("data", Gson().toJson(data))
                }
            }
        }
    }

    override fun initialize() {
        Gson().fromJson<Season>(
            requireArguments().getString("data")!!, Season::class.java
        ).apply {

            root.loadImage(photo)
            date.text = String.format("%s - %s", start_date.formatTime4(), end_date.formatTime4())
            name.text = title
            desc.text = short_description
            price_rub.text = price.toString()
            price_rub.showGone(price > 0)

            next.setOnClickListener {
                it.blockClickable()
                addFragment(PathTasksScreen.newInstance(this))
            }

            participate.setOnClickListener { inDevelopment(requireContext()) } // todo yandex payment

            if (is_available) {
                first.gone()
                deadline_layout.gone()
            } else {
                next.gone()

                val diff = (start_date.getTime2() - System.currentTimeMillis()) / 1000
                if (diff > 0) {
                    second.text = (diff % 60).toString()
                    minute.text = ((diff / 60) % 60).toString()
                    hour.text = ((diff / 60 / 60) % 60).toString()
                } else {
                    deadline_layout.gone()
                }
            }
        }
    }
}


class PathsPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data = arrayListOf<Season>()
    fun setData(data: ArrayList<Season>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = PathFragment.newInstance(data[position])

    override fun getCount(): Int = data.size

    override fun saveState(): Parcelable? = null

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }
}
