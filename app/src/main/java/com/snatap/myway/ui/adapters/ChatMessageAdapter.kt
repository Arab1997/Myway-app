package com.snatap.myway.ui.adapters

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.network.models.Chats
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.inflate
import com.snatap.myway.utils.extensions.loadImage
import com.snatap.myway.utils.extensions.showGone
import com.snatap.myway.utils.preferences.SharedManager
import kotlinx.android.synthetic.main.item_gallery.view.*
import kotlinx.android.synthetic.main.item_message_server.view.*


class ChatMessageAdapter(
    private var showUserName: Boolean,
    private val sharedManager: SharedManager
) :
    RecyclerView.Adapter<ViewHolder>() {

    private var data: ArrayList<Chats> = ArrayList()
    fun setData(newData: ArrayList<Chats>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent.inflate(
                when (viewType) {
                    Constants.MESSAGE_MY -> R.layout.item_message_my
                    Constants.MESSAGE_SERVER -> R.layout.item_message_server
                    Constants.IMAGE_MY -> R.layout.item_gallery
                    Constants.IMAGE_SERVER -> R.layout.item_gallery
                    else -> R.layout.item_message_center
                }
            )
        )

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position].user_id) {
            sharedManager.user.id -> Constants.MESSAGE_MY
            // todo for my and server images
            else -> Constants.MESSAGE_SERVER
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            data[holder.adapterPosition].apply {
                message?.text = text

                container?.let {
                    val params = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.gravity =
                        if (holder.itemViewType == Constants.IMAGE_MY) Gravity.END
                        else Gravity.START
                }
                userAvatar?.loadImage(user_avatar)
                userName?.showGone(showUserName)
                userName?.text = user_name
            }
        }
    }

}