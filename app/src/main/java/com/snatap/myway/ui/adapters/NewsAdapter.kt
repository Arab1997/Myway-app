package com.snatap.myway.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.snatap.myway.R
import com.snatap.myway.base.BaseAdapter
import com.snatap.myway.network.models.News
import com.snatap.myway.utils.common.ViewHolder
import com.snatap.myway.utils.diffutils.NewsDiffUtil
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    private val listener: (News) -> Unit, private val action: (Boolean, Int) -> Unit
) : BaseAdapter<News>(R.layout.item_news) {

    override fun setData(data: ArrayList<News>) {
        val diff = DiffUtil.calculateDiff(NewsDiffUtil(items, data))
        items.clear()
        items.addAll(data)
        diff.dispatchUpdatesTo(this)
    }

    override fun bindViewHolder(holder: ViewHolder, data: News) {
        holder.itemView.apply {
            data.apply {
                name.text = title
                image.loadImage(photo)
                author.loadImage(author_avatar)
                desc.text = short_description
                authorName.text = author_name
                createdDate.text = created_at.formatTime()

                commentCount.text = comments_count.toString()
                shareCount.text = shares_count.toString()
                mark.apply {

                    setOnClickListener {
                        it.blockClickable()
                        action.invoke(false, data.id)
                    }
                    setImageResource(if (is_bookmarked) R.drawable.ic_marked else R.drawable.ic_mark)
                }

                like.apply {
                    text = likes_count.toString()

                    if (is_liked) {
                        setDrawableStart(R.drawable.ic_liked)
                        setTextColorRes(R.color.red)
                    } else {
                        setDrawableStart(R.drawable.ic_like)
                        setTextColorRes(R.color.hint)
                    }

                    setOnClickListener {
                        it.blockClickable()
                        action.invoke(true, data.id)
                    }
                }

                recyclerTags.adapter = TagsAdapter().apply {
                    setData(ArrayList(tags))
                }
            }

            container.setOnClickListener { listener.invoke(data) }
        }
    }
}
