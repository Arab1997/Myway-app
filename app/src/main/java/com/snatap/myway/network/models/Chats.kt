package com.snatap.myway.network.models

data class ChatsResp(val success: Boolean, val chat_items: List<Chats>)
data class SendMessageChatResp(val success: Boolean, val chat_items: Chats)
data class ReadMessageChatResp(val success: Boolean, val unread_chat_messages_count: Int)

data class Chats(
    val admin_user_id: Int?,
    val created_at: String,
    val id: Int,
    val is_notified: Int,
    val is_read: Int,
    val store_item: Int?,
    val store_item_id: Int?,
    val store_item_type: Any?,
    val text: String,
    val updated_at: String,
    val user_avatar: String,
    val user_id: Int,
    val user_name: String
)