package com.snatap.myway.ui.screens.main.events

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.bottomsheet.BottomSheetRoundedFragment
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.bottomsheet_participants.*

class ParticipantsBottomSheet : BottomSheetRoundedFragment(R.layout.bottomsheet_participants) {

    override fun initialize() {

        recycler.adapter = Adapter(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, ""))

        ok.setOnClickListener {
            dismiss()
        }
    }
}

class Adapter(private val data: List<Any>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_participants))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            data[holder.adapterPosition].apply {
            }
        }
    }
}