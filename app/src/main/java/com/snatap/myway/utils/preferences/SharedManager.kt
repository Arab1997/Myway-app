package com.snatap.myway.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.snatap.myway.network.models.User
import com.snatap.myway.utils.preferences.PreferenceHelper.get
import com.snatap.myway.utils.preferences.PreferenceHelper.set

class SharedManager(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    private val context: Context
) {

    val default = PreferenceHelper.defaultPrefs(context)

    companion object {
        const val TOKEN = "TOKEN"
        const val CODE = "CODE"
        const val LANGUAGE = "LANGUAGE"
        const val USER = "USER"
        const val USER_ID = "USER_ID"
    }

    var token: String
        get() = preferences[TOKEN, ""]
        set(value) {
            preferences[TOKEN] = value
        }

    var code: String
        get() = default[CODE, ""]
        set(value) {
            default[CODE] = value
        }

    var userId: Int
        get() = preferences[USER_ID, 0]
        set(value) {
            preferences[USER_ID] = value
        }

    var user: User
        get() {
            val json = preferences.getString(USER, "")
            return gson.fromJson(json, User::class.java)
        }
        set(value) {
            preferences[USER] = gson.toJson(value)
        }

    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
