package com.snatap.myway.network.models

data class AchievementsResp(
    val success: Boolean,
    val user_achievements: List<UserAchievement>
)

data class UserAchievement(
    val active_image: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val inactive_image: String,
    val is_active: Boolean,
    val title: String,
    val type_id: Int,
    val updated_at: String
)