package com.snatap.myway.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(private val listener: (Any) -> Unit): RecyclerView.Adapter<ViewHolder>() {

    private var data = arrayListOf<Any>()



    public fun setData(data: ArrayList<Any>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
    )

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.removeLayout.visibility = View.GONE

        holder.itemView.setOnClickListener {
            listener.invoke(position)
        }

        holder.itemView.removeLayout.setOnClickListener {
            data.remove(position)
            val dataNew = ArrayList<Any>()
            for (i in 1 until data.size)
                if (i != position)
                    dataNew.add(data[i])
            data = dataNew

            notifyDataSetChanged()
        }
    }

}