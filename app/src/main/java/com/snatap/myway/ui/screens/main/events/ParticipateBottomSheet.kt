package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import kotlinx.android.synthetic.main.bottomsheet_participate.*

class ParticipateBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_participate) {

    private lateinit var listener: () -> Unit
    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun initialize() {

        pay.setOnClickListener {
            listener.invoke()
            closeSheet()
        }
    }
}