package com.snatap.myway.network.models

data class Tag(
    val created_at: String,
    val id: Int,
    val pivot: Pivot,
    val title: String,
    val updated_at: String
)

data class Pivot(
    val news_item_id: Int,
    val news_item_tag_id: Int
)