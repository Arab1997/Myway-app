package com.snatap.myway.network.models

data class StreamsResp(
    val streams: List<Stream>,
    val success: Boolean
)

data class Stream(
    val admin_user_id: Int,
    val author_avatar: String,
    val author_name: String,
    val created_at: String,
    val date: String,
    val direct_url: Any,
    val direct_url_expires: Any,
    val free_duration: String,
    val free_duration_seconds: Int,
    val id: Int,
    val is_ended: Boolean,
    val is_reserved: Boolean,
    val is_viewable: Boolean,
    val max_users: Int,
    val needs_push: Int,
    val notify_at: String,
    val online_users_count: Int,
    val photo: String,
    val reservation_reward: Int,
    val reservations_count: Int,
    val tags: List<Tag>,
    val title: String,
    val updated_at: String,
    val youtube_id: String,
    val youtube_url: String
)

data class LastStream(val id: Int, val url: String)
