package com.snatap.myway.network.models

data class NewsDetailResp(val success: Boolean, val news_item: NewsDetail)

data class NewsDetail(
    val admin_user_id: Int,
    val author_avatar: String,
    val author_name: String,
    val comments: List<Comment>,
    val comments_count: Int,
    val created_at: String,
    val datetime: String,
    val description: String,
    val id: Int,
    val quiz_id: Int?,
    val is_bookmarked: Boolean,
    val is_liked: Boolean,
    val likes_count: Int,
    val needs_push: Int,
    val notify_at: String,
    val photo: String,
    val related: List<News>,
    val sharing: Boolean,
    val short_description: String,
    val tags: List<Tag>,
    val title: String,
    val updated_at: String,
    val video: String
)
