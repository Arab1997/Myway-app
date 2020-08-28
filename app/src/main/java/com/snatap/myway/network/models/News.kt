package com.snatap.myway.network.models

data class NewsResp(val success: Boolean, val news_items: ArrayList<News>)

data class News(
    val id: Int,
    val title: String,
    val short_description: String,
    val description: String,
    val photo: String,
    val video: String,
    val admin_user_id: Int,
    val needs_push: Int,
    val notify_at: String,
    val sharing: Boolean,
    val datetime: String,
    val quiz_id: Any?,
    val created_at: String,
    val updated_at: String,
    val author_name: String,
    val author_avatar: String,
    val likes_count: Int,
    val is_liked: Boolean,
    val is_bookmarked: Boolean,
    val is_shared: Boolean,
    val comments_count: Int,
    val shares_count: Int,
    val tags: List<Tag>
)
