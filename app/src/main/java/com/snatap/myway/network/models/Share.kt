package com.snatap.myway.network.models

data class Share(
    val is_shared: Boolean,
    val shares_count: Int,
    val success: Boolean
)