package com.snatap.myway.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.snatap.myway.R

fun playStoreUrl(context: Context) =
    "https://play.google.com/store/apps/details?id=${context.packageName}"

fun shareText(context: Context, text: String) {
    val recommend = "$text в приложение ${context.getString(R.string.app_name)}\n"
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, "Мой Реферрал $recommend")
    sendIntent.type = "text/plain"
    context.startActivity(sendIntent)
}

fun rateApp(context: Context) =
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=PackageName")))

fun showAbout(activity: Activity) {
    AlertDialog.Builder(activity)
        .setCancelable(true)
        .setTitle(activity.getString(R.string.app_name))
        .setMessage(
            "\nby MukhammadRasul\n\nРазработано в ООО \"ML-Reactive\" "
        )
        .create().show()
}
