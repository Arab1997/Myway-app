package com.snatap.myway.ui.screens.main.breakthrough

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.*
import com.snatap.myway.ui.screens.main.path.LessonDetailScreen
import kotlinx.android.synthetic.main.screen_break_visual.*
import kotlinx.android.synthetic.main.screen_break_visual.recyclerChamps

class VisualizationBreakthroughScreen : BaseFragment(R.layout.screen_break_visual) {
    override fun initialize() {
       // setClicks()
        initViews()
    }

private fun initViews() {

    recyclerPrizes.adapter = PrizesAdapter {
        addFragment(PrizesScreen())
    }.apply {
        setData(arrayListOf(1, 2, 3))
    }

    recyclerChamps.adapter = ChampsAdapter {
        addFragment(ChampsDetailsScreen())
    }.apply {
        setData(arrayListOf(1, 2, 3))
    }


    recyclerFriends.adapter = FriendsAdapter {
        // addFragment(LessonDetailScreen())
        addFragment(RatingDetailScreen())
    }.apply {
        setData(arrayListOf(1, 2, 3))
    }


}

private fun setClicks() {
  //  container2.setOnClickListener { addFragment(ChampsScreen()) }

}
}


