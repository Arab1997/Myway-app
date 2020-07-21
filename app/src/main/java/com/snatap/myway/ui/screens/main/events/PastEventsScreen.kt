package com.snatap.myway.ui.screens.main.events

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.inflate
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.fragment_past_events.*

class PastEventsScreen : BaseFragment(R.layout.screen_past_events) {

    override fun initialize() {

        title.text = "Прошедшие события"

        back.setOnClickListener { finishFragment() }

        right.apply {
            setImageResource(R.drawable.ic_filter_white)
            setOnClickListener {
                val bottomSheet = FilterBottomSheet.newInstance(false).apply {
                    setListener {
                        inDevelopment(requireContext())
                    }
                }
                bottomSheet.show(childFragmentManager, "")
            }
        }

        recycler.adapter = Adapter {
            inDevelopment(requireContext())
        }.apply { setData(arrayListOf(1, 2, 3, 4, 5, 6)) }
    }


    class Adapter(private val listener: (Any) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

        private var data = arrayListOf<Any>()
        fun setData(data: ArrayList<Any>) {
            this.data = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            parent.inflate(R.layout.item_past_events)
        )

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.apply {
                data[holder.adapterPosition].apply {
/*
                    image.loadImage(img)
                    name.text = title
                    date.text = desc
*/

                    setOnClickListener { listener.invoke(this) }
                }
            }
        }
    }
}