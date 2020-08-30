package com.snatap.myway.utils.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
val serverDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ru"))

@SuppressLint("SimpleDateFormat")
val userDF = SimpleDateFormat("HH:mm, dd MMM", Locale("ru"))

@SuppressLint("SimpleDateFormat")
val userDF2 = SimpleDateFormat("dd MM yyyy", Locale("ru"))

@SuppressLint("SimpleDateFormat")
val userDF3 = SimpleDateFormat("EE, HH:mm", Locale("ru"))

fun String.formatTime(): String = userDF.format(serverDF.parse(this)!!)
fun String.formatTime2(): String = userDF2.format(serverDF.parse(this)!!)
fun String.formatTime3(): String = userDF3.format(serverDF.parse(this)!!)
fun String.getTime(): Long {
    serverDF.timeZone = TimeZone.getTimeZone("GMT")
    return serverDF.parse(this)!!.time
}

@SuppressLint("SimpleDateFormat")
fun utcToLocal(utcTime: String): Long {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val value = formatter.parse(utcTime)!!
    return value.time
}