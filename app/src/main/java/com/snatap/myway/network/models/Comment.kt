package com.snatap.myway.network.models

data class CommentsResp(val success: Boolean, val news_item_comments: List<Comment>)
data class AddCommentResp(val success: Boolean, val news_item_comment: Comment)

data class Comment(
    val id: Int,
    val news_item_id: Int,
    val text: String,
    val created_at: String,
    val updated_at: String,
    val user: User?,
    val user_id: Int,
    val user_avatar: String,
    val user_name: String
)
