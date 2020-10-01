package com.snatap.myway.utils.emoji


import android.content.Context
import android.graphics.Color
import android.graphics.RectF
import android.text.SpannableString
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.sqrt


fun View.dpToPx(dp: Float): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun MotionEvent.inside(bounds: RectF, startAngle: Float, sweepAngle: Float): Boolean {
    val angle = Math.toDegrees(atan2((y - bounds.centerY()).toDouble(), (x - bounds.centerX()).toDouble())).toFloat()
    return angleBetween(startAngle, startAngle + sweepAngle, angle)
}

fun angleBetween(startAngle: Float, endAngle: Float, angle: Float): Boolean {
    val angleNormalized = (angle + 360) % 360
    val startNormalized = (startAngle + 360) % 360
    val endNormalized = (endAngle + 360) % 360

    return if (angleNormalized in startNormalized..endNormalized) // normal range check
        true
    else startNormalized > endNormalized && (angleNormalized >= startNormalized || angleNormalized <= endNormalized)
}

fun MotionEvent.inside(cx: Float, cy: Float, radius: Float): Boolean {
    val dx = cx - x.toDouble()
    val dy = cy - y.toDouble()
    return sqrt(dx * dx + dy * dy) <= radius
}

internal fun getCorrectColor(color0: Int, color1: Int, percentage: Float): Int {
    val red = Color.red(color0)
    val green = Color.green(color0)
    val blue = Color.blue(color0)
    return Color.rgb(
        red + ((Color.red(color1) - red).toFloat() * percentage).toInt(),
        green + ((Color.green(color1) - green).toFloat() * percentage).toInt(),
        blue + ((Color.blue(color1) - blue).toFloat() * percentage).toInt()
    )
}

internal fun Context.getWidthPixels(): Int = this.resources.displayMetrics.widthPixels


