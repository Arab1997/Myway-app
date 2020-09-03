package com.snatap.myway.network.models

data class NewsCommentsResp(val success: Boolean, val news_item_comments: List<Comment>)
data class AddCommentNewsResp(val success: Boolean, val news_item_comment: Comment)

data class EventsCommentsResp(val success: Boolean, val event_comments: List<Comment>)
data class AddCommentEventsResp(val success: Boolean, val event_comment: Comment)

data class Comment(
    val id: Int,
    val news_item_id: Int,
    val event_id: Int,
    val text: String,
    val created_at: String,
    val updated_at: String,
    val user: User?,
    val user_id: Int,
    val user_name: String?
)
