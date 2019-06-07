package com.ee.core.extension

import android.content.Context
import android.widget.Toast


/**
 * extension to show toast for Any object
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, message, duration).apply { show() }
}




