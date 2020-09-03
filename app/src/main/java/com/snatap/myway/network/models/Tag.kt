package com.snatap.myway.network.models

data class NewsTagResp(
    val success: Boolean,
    val news_item_tags: List<Tag>
)

data class EventsTagResp(
    val success: Boolean,
    val event_tags: List<Tag>
)

data class Tag(
    val created_at: String,
    val id: Int,
    val pivot: Pivot,
    val icon: String,
    val title: String,
    var color: Int,
    var isChecked: Boolean,
    val updated_at: String
)

data class Pivot(
    val news_item_id: Int,
    val news_item_tag_id: Int
)