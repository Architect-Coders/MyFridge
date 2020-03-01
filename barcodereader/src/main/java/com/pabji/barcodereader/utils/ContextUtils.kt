package com.pabji.barcodereader.utils

import android.content.Context
import android.content.res.Configuration

fun Context.isPortrait(): Boolean = with(resources.configuration.orientation) {
    when (this) {
        Configuration.ORIENTATION_PORTRAIT -> true
        else -> false
    }
}