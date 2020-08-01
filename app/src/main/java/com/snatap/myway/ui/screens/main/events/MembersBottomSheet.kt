package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.ui.adapters.MembersAdapter
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import kotlinx.android.synthetic.main.bottomsheet_members.*

class MembersBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_members) {

    override fun initialize() {

        recycler.adapter = MembersAdapter().apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, ""))
        }

        ok.setOnClickListener {
            dismiss()
        }
    }
}
