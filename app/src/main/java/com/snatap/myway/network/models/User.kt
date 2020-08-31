package com.snatap.myway.network.models

data class UserResp(val success: Boolean, val user: User)

data class User(
    val address: String?,
    val avatar: String,
    val coins: Int,
    val created_at: String,
    val email: String?,
    val full_name: String?,
    val group_id: Int?,
    val id: Int,
    val instagram: String?,
    val is_premium: Boolean,
    val gender: String,
    val is_premium_updated_at: String?,
    val max_month_coins: Int,
    val phone: String,
    val promocode: String,
    val referrer_id: Any?,
    val sms_code: Any?,
    val timezone: String,
    val timezone_updated_at: String?,
    val job: String?,
    val date_of_birth: String?,
    val post_index: String?,
    val updated_at: String
)

data class UserRequest(
    val full_name: String?,
    val instagram: String?,
    val address: String?,
    val email: String?,
    val phone: String?,
    val job: String?,
    val date_of_birth: String?,
    val post_index: String?
)