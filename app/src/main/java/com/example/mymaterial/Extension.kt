package com.example.mymaterial

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.app.ActivityCompat

fun Activity.requestPermission(vararg permissions: String): Boolean {
    return if (!hasPermissions(this, *permissions)) {
        ActivityCompat.requestPermissions(this, permissions, 1)
        false
    } else
        true
}

private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
    for (permission in permissions)
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
            return false
    return true
}

fun View?.alphaAnimation(show: Boolean, alphaStart: Float = 1.0f,
                         alphaEnd: Float = 0.1f, duration: Long = 1000) {
    this?.clearAnimation()
    if (show) {
        val alpha = AlphaAnimation(alphaStart, alphaEnd)
        alpha.duration = duration
        alpha.repeatCount = Animation.INFINITE
        alpha.repeatMode = Animation.REVERSE
        this?.animation = alpha
        alpha.start()
    }
}

fun String.contains(list: Array<String>): Boolean {
    return list.any { contains(it) }
}

