package com.snatap.myway.ui.screens.main.breakthrough

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.Constraints.TAG
import com.hsalf.smileyrating.SmileyRating
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.FriendsAdapter
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.*
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.cart
import kotlinx.android.synthetic.main.content_rounded_toolbar_events.message
import kotlinx.android.synthetic.main.fragment_wheel_of_fortune.*
import kotlinx.android.synthetic.main.screen_break_visual.*


class WheelFortuneScreen : BaseFragment(R.layout.fragment_wheel_of_fortune) {

    companion object {
        private var txtTitle: String? = null
        fun newInstance(txtTitle: String): WheelFortuneScreen {
            Companion.txtTitle = txtTitle
            return WheelFortuneScreen()
        }
    }

    override fun initialize() {

        initViews()
        //  setClicks()
        title.text = "Колесо фортуны MyWay"
        cart.setOnClickListener { addFragment(StoreScreen()) }
        message.setOnClickListener { addFragment(ChatScreen()) }
    }

    private fun initViews() {
        recyclerFriends.adapter = FriendsAdapter {
            // addFragment(LessonDetailScreen())
            addFragment(RatingDetailScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3))
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        wheel_view.setOnClickListener {
        }
        wheel_view.titles = listOf("Упс", "+12 ", "-5", "Подарок", "Промокод", "Упс", "-5")


        smile_rating.setSmileySelectedListener {
            if (SmileyRating.Type.GREAT == it) {
                Log.i(TAG, "Wow, the user gave high rating");
            }

        }

      /*  smile_rating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                // You can compare it with rating Type
                if (SmileyRating.Type.GREAT == type) {
                    Log.i(TAG, "Wow, the user gave high rating");
                }
                // You can get the user rating too
                // rating will between 1 to 5
                int rating = type.getRating();
            }
        });
*/
    }

    private fun setClicks() {
    }
}













