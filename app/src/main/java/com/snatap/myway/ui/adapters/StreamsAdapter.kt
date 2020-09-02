package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import android.os.CountDownTimer
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.Stream
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_duration.view.*
import kotlinx.android.synthetic.main.item_streams.view.*

class StreamsAdapter(
    private var isAnnouncementStream: Boolean,
    private val listener: (Stream) -> Unit
) : BaseAdapter<Stream>(R.layout.item_streams) {

    override fun bindViewHolder(holder: ViewHolder, data: Stream) {
        holder.itemView.apply {
            data.apply {

                if (!isAnnouncementStream) setOnClickListener { listener.invoke(this) }
                img.loadImage(photo)
                name.text = title
                userDate.text = created_at.formatTime()
                val iconList = ArrayList(tags.filter { it.icon.isNotEmpty() })
                icons.adapter = TagIconsAdapter().apply { setData(iconList) }
                icons.showGone(iconList.isNotEmpty())

                if (isAnnouncementStream)
                    timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                        @SuppressLint("SetTextI18n")
                        override fun onTick(millisUntilFinished: Long) {
                            diff = (date.getTime() - System.currentTimeMillis()) / 1000
                            s = diff % 60
                            m = (diff / 60) % 60
                            h = (diff / 60 / 60) % 60
                            announcementTime.text = String.format("%02d : %02d : %02d", h, m, s)
                        }

                        override fun onFinish() {
                        }
                    }.start()
            }

            announcement.showGone(isAnnouncementStream)
            register.showGone(isAnnouncementStream)

            dur.gone() // todo set duration time
            userDate.showGone(!isAnnouncementStream)

        }
    }

    private var h: Long = 0
    private var m: Long = 0
    private var s: Long = 0
    private var diff: Long = 0
    private lateinit var timer: CountDownTimer
}

