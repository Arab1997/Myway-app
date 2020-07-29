package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.ui.adapters.ParticipantsAdapter
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import kotlinx.android.synthetic.main.bottomsheet_participants.*

class ParticipantsBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_participants) {

    override fun initialize() {

        recycler.adapter = ParticipantsAdapter(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, ""))

        ok.setOnClickListener {
            dismiss()
        }
    }
}
