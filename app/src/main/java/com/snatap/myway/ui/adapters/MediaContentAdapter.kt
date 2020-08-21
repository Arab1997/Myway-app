package com.snatap.myway.ui.adapters

import android.annotation.SuppressLint
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.LessonDay
import com.snatap.myway.utils.Constants
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.extensions.fromHtml
import com.snatap.myway.utils.extensions.loadImage
import kotlinx.android.synthetic.main.item_content.view.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class MediaContentAdapter : BaseAdapter<LessonDay>(R.layout.item_content) {

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("HH:mm:ss")

    private fun generateDuration(start: String, end: String): String {
        val dur = abs(sdf.parse(start)!!.time - sdf.parse(end)!!.time)
        val h = TimeUnit.MILLISECONDS.toHours(dur)
        val m = TimeUnit.MILLISECONDS.toMinutes(dur) % 60
        return if (h > 0) "$h час $m минут" else "$m минут"
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: LessonDay) {
        holder.itemView.apply {

            data.let {
                title1.text = it.title
                title2.text = it.title
                desc.text = it.text.fromHtml()
                image.loadImage(it.photo)

                duration.text = generateDuration(it.start_time, it.end_time)
                duration1.text =
                    "${it.start_time.removeSuffix(":00")} - ${it.end_time.removeSuffix(":00")}"

                when (it.type) {
                    Constants.DAILY_CONTENT_TYPE_MORNING_VIP -> {
                        typeName.text = "Утренний"
                        typeImg.setImageResource(R.drawable.ic_morning)
                    }
                    Constants.DAILY_CONTENT_TYPE_DAY -> {
                        typeName.text = "Дневной"
                        typeImg.setImageResource(R.drawable.ic_day)
                    }
                    Constants.DAILY_CONTENT_TYPE_DAY_VIP -> {
                        typeName.text = "Дневной"
                        typeImg.setImageResource(R.drawable.ic_day)
                    }
                    Constants.DAILY_CONTENT_TYPE_EVENING_VIP -> {
                        typeName.text = "Вечерний"
                        typeImg.setImageResource(R.drawable.ic_night)
                    }
                    else -> {
                        typeName.text = "UNKNOWN"
                        typeImg.setImageResource(0)
                    }
                }
            }
            setOnClickListener {
                expandableLayout.toggle()

                recyclerTask.adapter = TaskAdapter()
                    .apply { setData(arrayListOf(1, 2, 3, 4, 5)) }
            }
        }
    }
}