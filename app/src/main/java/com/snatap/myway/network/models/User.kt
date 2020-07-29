package com.snatap.myway.network.models

data class User(
    val address: Any,
    val avatar: String,
    val coins: Int,
    val created_at: String,
    val email: String,
    val full_name: String,
    val group_id: Any,
    val id: Int,
    val instagram: Any,
    val is_premium: Boolean,
    val is_premium_updated_at: Any,
    val max_month_coins: Int,
    val phone: String,
    val promocode: String,
    val referrer_id: Any,
    val sms_code: Any,
    val timezone: String,
    val timezone_updated_at: Any,
    val updated_at: String
)