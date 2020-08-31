package com.snatap.myway.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.snatap.myway.network.models.User
import com.snatap.myway.utils.preferences.PreferenceHelper.get
import com.snatap.myway.utils.preferences.PreferenceHelper.set

class SharedManager(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    context: Context
) {

    val default = PreferenceHelper.defaultPrefs(context)

    companion object {
        const val TOKEN = "TOKEN"
        const val CODE = "CODE"
        const val LANGUAGE = "LANGUAGE"
        const val USER = "USER"
        const val FINISHED_TASKS = "FINISHED_TASKS"
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

    var user: User
        get() {
            val json = preferences.getString(USER, "")
            return gson.fromJson(json, User::class.java)
        }
        set(value) {
            preferences[USER] = gson.toJson(value)
        }

    var finishedTasks: ArrayList<Int>
        get() {
            val json = preferences.getString(FINISHED_TASKS, "")
            return if (json == "") arrayListOf()
            else gson.fromJson(json, object : TypeToken<List<Int>>() {}.type)

        }
        set(value) {
            preferences[FINISHED_TASKS] = gson.toJson(value)
        }

    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
