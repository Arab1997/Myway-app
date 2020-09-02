package com.snatap.myway.network.models

data class TagResp(
    val success: Boolean,
    val news_item_tags: List<Tag>
)

data class Tag(
    val created_at: String,
    val id: Int,
    val pivot: Pivot,
    val icon: String,
    val title: String,
    var isChecked: Boolean,
    val updated_at: String
)

data class Pivot(
    val news_item_id: Int,
    val news_item_tag_id: Int
)