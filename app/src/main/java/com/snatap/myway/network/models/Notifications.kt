package com.snatap.myway.network.models

data class NotificationsResp(
    val success: Boolean,
    val user_notifications: List<Notification>
)

data class Notification(
    val created_at: String,
    val description: String,
    val id: Int,
    val is_read: Boolean,
    val related_data: Any?,
    val related_id: Int,
    val related_type: String,
    val title: String,
    val updated_at: String,
    val user_id: Int
)