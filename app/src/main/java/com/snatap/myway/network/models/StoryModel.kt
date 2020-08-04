package com.snatap.myway.network.models

data class StoryModel(
    val image: Int = 0,
    val time: Int = 0,
    val comments: ArrayList<Comment> = arrayListOf()
)

