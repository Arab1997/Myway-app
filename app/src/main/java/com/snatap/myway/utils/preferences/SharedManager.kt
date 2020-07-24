package com.snatap.myway.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import com.snatap.myway.utils.preferences.PreferenceHelper.get
import com.snatap.myway.utils.preferences.PreferenceHelper.set
import com.google.gson.Gson

class SharedManager(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    private val context: Context
) {

    companion object {
        const val TOKEN = "TOKEN"
        const val CODE = "CODE"
        const val LANGUAGE = "LANGUAGE"
        const val USER = "USER"
    }

    var token: String
        get() = preferences[TOKEN, ""]
        set(value) {
            preferences[TOKEN] = value
        }

    var code: String
        get() = preferences[CODE, ""]
        set(value) {
            preferences[CODE] = value
        }

/*
    var user: User?
        get() {
            val json = preferences.getString(USER, "")
            return gson.fromJson(json, User::class.java)
        }
        set(value) {
            preferences[USER] = gson.toJson(value)
        }*/

    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
