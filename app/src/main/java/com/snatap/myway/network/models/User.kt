package com.snatap.myway.network.models

data class UserResp(val success: Boolean, val user: User)

data class User(
    val address: String?,
    val avatar: String,
    val coins: Int,
    val created_at: String,
    val email: String,
    val full_name: String,
    val group_id: Int?,
    val id: Int,
    val instagram: String?,
    val is_premium: Boolean,
    val is_premium_updated_at: String?,
    val max_month_coins: Int,
    val phone: String,
    val promocode: String,
    val referrer_id: Any?,
    val sms_code: Any?,
    val timezone: String,
    val timezone_updated_at: String?,
    val updated_at: String
)