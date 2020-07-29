package com.snatap.myway.network.models

data class Like(
    val is_liked: Boolean,
    val likes_count: Int,
    val success: Boolean
)