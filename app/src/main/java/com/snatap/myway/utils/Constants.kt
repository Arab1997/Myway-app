package com.snatap.myway.utils

import com.snatap.myway.BuildConfig

object Constants {

    const val BASE_URL = BuildConfig.BASE_URL
    const val TIMEOUT = 20.toLong()

    const val MESSAGE_MY = 100
    const val MESSAGE_SERVER = 101
    const val MESSAGE_CENTER = 102
    const val IMAGE_MY = 103
    const val IMAGE_SERVER = 104

    const val IS_REGISTER_KEY = "register"
}

data class KeyValue(val key: String, val value: String)
