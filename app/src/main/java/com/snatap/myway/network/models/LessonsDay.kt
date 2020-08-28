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
    val video: String?,
    val audio: String?,
    var training_items: List<Training>?
)

data class Training(
    val audio: Any,
    val created_at: String,
    val duration: String,
    val id: Int,
    val is_break: Boolean,
    val lesson_day_item_id: Int,
    val lesson_item_id: Any,
    val lesson_library_item_id: Any,
    val order: Int,
    val photo: String,
    val selected: Int,
    val title: String,
    val updated_at: String,
    val video: String,
    var playing: Boolean = false,
    var finished: Boolean = false
)
