package com.snatap.myway.network.models

data class LessonsDayResp(
    val lesson_day_items: List<LessonDay>,
    val success: Boolean
)

data class LessonDay(
    val created_at: String,
    val date: String,
    val end_time: String,
    val id: Int,
    val needs_push: Boolean,
    val notify_at: String,
    val photo: String,
    val reward: Int,
    val start_time: String,
    val text: String,
    val title: String,
    val type: String,
    val updated_at: String,
    val video: String
)