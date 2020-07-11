package com.snatap.myway.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.snatap.myway.R

fun ImageView.loadImage(src: String) {
    Glide.with(context)
        .load(src)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions().error(R.drawable.logo))
        .into(this)
}

fun ImageView.loadFromResources(image: Int) {
    Glide.with(context)
        .load(resources.getDrawable(image))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(this)
}

