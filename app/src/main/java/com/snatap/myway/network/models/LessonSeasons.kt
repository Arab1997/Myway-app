package com.snatap.myway.network.models

data class SeasonsResp(
    val lesson_seasons: List<Season>,
    val success: Boolean
)

data class SeasonResp(
    val lesson_season: Season,
    val success: Boolean
)

data class Season(
    val id: Int,
    val title: String,
    val short_description: String,
    val photo: String,
    val start_date: String,
    val end_date: String,
    val price: Int,
    val is_available: Boolean,
    val likes_count: Int,
    val is_liked: Boolean,
    val created_at: String,
    val updated_at: String,
    val lesson_items: List<Lesson>?
)

data class Lesson(
    val assignment_text: String,
    val created_at: String,
    val datetime: String,
    val id: Int,
    val is_liked: Boolean,
    val lesson_reports: List<LessonReport>,
    val likes_count: Int,
    val needs_push: Boolean,
    val notify_at: Any,
    val photo: String,
    val pinned: Boolean,
    val pivot: Pivot,
    val report_deadline_at: String,
    val require_report: Boolean,
    val reward: Int,
    val tags: List<Tag>,
    val text: String,
    val title: String,
    val total_duration: Int,
    val training_items_count: Int,
    val type: Int,
    val updated_at: String,
    val video: String?,
    var training_items: List<Training>?

)

data class LessonReport(
    val accepted: Int,
    val created_at: String,
    val id: Int,
    val items: List<ReportItem>,
    val lesson_item_id: Int,
    val status: Int,
    val text: String,
    val updated_at: String,
    val user_id: Int
)

data class ReportItem(
    val created_at: String,
    val file: String?,
    val id: Int,
    val lesson_report_id: Int,
    val photo: String?,
    val updated_at: String,
    val video: String?,
    val video_preview: String?
)