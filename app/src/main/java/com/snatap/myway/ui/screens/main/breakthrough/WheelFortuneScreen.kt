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

class WheelFortuneScreen : BaseFragment(R.layout.fragment_wheel_of_fortune) {

    override fun initialize() {

        initViews()
    }

    private fun initViews() {

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

}






