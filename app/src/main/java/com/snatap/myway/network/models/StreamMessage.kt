package com.snatap.myway.network.models

data class StreamUrlResp(
    val url: String,
    val success: Boolean
)

data class StreamMessageResp(
    val stream_chat_item: StreamMessage,
    val success: Boolean
)

data class StreamMessagesResp(
    val stream_chat_items: List<StreamMessage>,
    val success: Boolean
)

data class StreamMessage(
    val admin_user_id: Any?,
    val created_at: String,
    val file: Any?,
    val id: Int,
    val stream_id: Int,
    val text: String,
    val updated_at: String,
    val user_avatar: String,
    val user_id: Int,
    val user_name: String
)